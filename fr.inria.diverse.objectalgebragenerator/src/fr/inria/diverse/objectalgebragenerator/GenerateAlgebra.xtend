package fr.inria.diverse.objectalgebragenerator

import fr.inria.diverse.objectalgebragenerator.Graph.GraphNode
import java.util.Comparator
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Map.Entry
import java.util.Set
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import java.util.Collection
import org.eclipse.emf.ecore.EPackage.Descriptor

class GenerateAlgebra {

	private def Node<EClass> addChildren(EClass e, List<EClass> allElems) {
		val ret = new Node(e)
		val subtypes = allElems.filter[f|f.ESuperTypes.contains(e)]
		subtypes.filter[x|!x.isRoot].map[f|addChildren(f, allElems)].forEach[x|ret.addChild(x)]
		ret
	}
	
	public def Graph<EClass> buildGraph(EPackage ePackage) {
		val graph1 = new Graph<EClass>()
		visitPackages(newHashSet(), ePackage, graph1)
		graph1	
	}
	
	private def Map<String, List<GraphNode<EClass>>> buildAllTypes(List<List<GraphNode<EClass>>> clusters) {
		clusters.toMap(new CharsSequence)
	}
	
	private def List<List<GraphNode<EClass>>> calculateClusters(Graph<EClass> graphCurrentPackage) {
		graphCurrentPackage.clusters().map[x|x.sortBy[y|y.elem.name]].sortBy[z|z.head.elem.name].toList		
	}
	
	private def buildConcretTypeForParents(EPackage ePackage, Map<String, List<GraphNode<EClass>>> allTypes) {
		val graphCurrentPackage = buildGraph(ePackage)

		val  clusters = calculateClusters(graphCurrentPackage)
		
		clusters.map[x | x.filter[z|!z.elem.abstract].head.elem.abstractType(allTypes)]
	}
	
	
	def calculateAllTypes(EPackage ePackage, boolean global) {
		buildConcretTypes(buildAllTypes(calculateClusters(buildGraph(ePackage)))).mapValues[e|if(global) e else e.filter[f|f.elem.EPackage.equals(ePackage)]].
			filter[p1, p2|!p2.empty]
			
		}
		
	def String processConcreteOperation(GraphNode<EClass> entry, EPackage epackage) {
		val clazz = entry.elem
		val all$TypesGlobal = calculateAllTypes(epackage, true) 
		'''
		package «epackage.name».algebra.impl.operation;
		
		public class «clazz.EPackage.name.toFirstUpper»«clazz.name.toFirstUpper»Operation implements «epackage.name».algebra.operation.«epackage.name.toFirstUpper + clazz.name.toFirstUpper+"Operation"» {
			
			private final «clazz.javaFullPath» self;
			private final «epackage.name».algebra.«epackage.name.toFirstUpper»Algebra«FOR x: all$TypesGlobal.entrySet BEFORE '<' SEPARATOR ', ' AFTER '>'»? extends «x.value.findRootType.operationFullPath(epackage)»«ENDFOR» algebra;
			
			public «clazz.EPackage.name.toFirstUpper»«clazz.name.toFirstUpper»Operation(final «clazz.javaFullPath» self, final «epackage.name».algebra.«epackage.name.toFirstUpper»Algebra«FOR x: all$TypesGlobal.entrySet BEFORE '<' SEPARATOR ', ' AFTER '>'»? extends «x.value.findRootType.operationFullPath(epackage)»«ENDFOR» algebra) {
				this.self = self;
				this.algebra = algebra;
			}
		}
		'''
	
	}

	def String processConcreteAlgebra(EPackage ePackage) { 
	
		/*
		 * Here we have to generate one method per class
		 */
	
		val typez = buildGraph(ePackage) 
		val all$TypesGlobal = calculateAllTypes(ePackage, true) 
	
		'''
		package «ePackage.name».algebra.impl;
		
		public interface «ePackage.name.toFirstUpper»AlgebraImpl extends «ePackage.name».algebra.«ePackage.name.toFirstUpper»Algebra«FOR x: all$TypesGlobal.values BEFORE '<' SEPARATOR ', ' AFTER '>'»«x.findRootType.operationFullPath(ePackage)»«ENDFOR» {
			«FOR x: typez.nodes»
			@Override
			default «ePackage.name».algebra.impl.operation.«x.elem.EPackage.name.toFirstUpper»«x.findRootType(all$TypesGlobal).name.toFirstUpper»Operation «x.findRootType(all$TypesGlobal).name.toFirstLower»(final «x.elem.javaFullPath» «x.elem.name») {
				return new «ePackage.name».algebra.impl.operation.«x.elem.EPackage.name.toFirstUpper»«x.findRootType(all$TypesGlobal).name.toFirstUpper»Operation(«x.findRootType(all$TypesGlobal).name», this);
			} 
			«ENDFOR»
		}
		'''
	}
	
	private def EClass findRootType(GraphNode<EClass> node, Map<String, Iterable<GraphNode<EClass>>> clusters) {
		clusters.values.filter[i | i.exists[e | e.elem === node.elem]].head.findRootType
	}
	
	
	private def Collection<EClass> ancestors(EClass clazz) {
		val ret = newHashSet()
		if(!clazz.ESuperTypes.empty) {
			clazz.ESuperTypes.forEach[st|
				ret.add(st) 
				ret.addAll(st.ancestors)
			]
		}
		
		ret
	}
	
	
	def allClasses(EPackage ePackage) {
		ePackage.eAllContents.filter[e|e instanceof EClass].map[e|e as EClass].toList.sortBy[e|e.name]	
	}
	
	def String genericType(EClass clazz) 
		'''«clazz.EPackage.name.replaceAll("\\.","").toFirstUpper»__«clazz.name»T'''
	
	

	def String processAlgebra(EPackage ePackage) {
		
		val allEClasses = ePackage.allClasses
		val graph = buildGraph(ePackage)
		val allMethods = graph.nodes.sortBy[e|e.elem.name].filter[e|e.elem.EPackage.equals(ePackage)].filter [e|
			!e.elem.abstract
		] 
		val allDirectPackages = allMethods.allDirectPackages(ePackage)
//		val allWithHierarchy = newHashSet()
//		allWithHierarchy.addAll(allMethods.map[elem])
//		allWithHierarchy.addAll(allMethods.map[x|x.elem.ancestors].flatten)
		
		// TODO: refine the $ dispatch methods.
		// Note: We have to redefine the dispatch for every newly introduced class and all its ancestors (since now we have to dispatch to X and Ancestors_X methods newly added)
		// If the instance given at a given level is not from the currently handled class (the newly defined), it has to be delegated to the relevant parent interface.
		// TODO: Replace the ${ClassName}T convention since two classes can have the same name in different packages.
		// TODO: choose which usecase to work on
		
		'''
		package «ePackage.name».algebra;
		
		«FOR clazz: allEClasses»
		import «clazz.javaFullPath»;
		«ENDFOR»
		
		public interface «ePackage.toPackageName»«FOR clazz: graph.nodes BEFORE '<' SEPARATOR ',' AFTER '>'»«clazz.elem.genericType»«ENDFOR»
			«FOR ePp : allDirectPackages.sortBy[name] BEFORE ' extends ' SEPARATOR ', '»«ePp.name».algebra.«ePp.toPackageName»«FOR x : ePp.allClasses BEFORE '<' SEPARATOR ', ' AFTER '>'»«x.genericType»«ENDFOR»«ENDFOR» {
			«FOR clazzNode:allMethods»
			«clazzNode.elem.genericType» «clazzNode.elem.name.toFirstLower»(final «clazzNode.elem.name» «clazzNode.elem.name.toFirstLower»);
			«FOR parent: clazzNode.elem.ancestors»
			«parent.genericType» «parent.name.toFirstLower»_«clazzNode.elem.name.toFirstLower»(final «clazzNode.elem.name» «clazzNode.elem.name.toFirstLower»);
			«ENDFOR»
			
			«ENDFOR»
			
			«FOR clazz:graph.nodes»
			default «clazz.elem.genericType» $(«clazz.elem.name» «clazz.elem.name.toFirstLower») {
				«FOR subClazz:clazz.incomings.filter[sc|!sc.elem.abstract]»
				if(«clazz.elem.name.toFirstLower» instanceof «subClazz.elem.name») return «clazz.elem.name.toFirstLower»_«subClazz.elem.name.toFirstLower»((«subClazz.elem.name») «subClazz.elem.name.toFirstLower»);
				«ENDFOR»
				«IF clazz.elem.abstract»
				return null;
				«ELSE»
				return «clazz.elem.name.toFirstLower»(«clazz.elem.name.toFirstLower»);
				«ENDIF»
			}
			«ENDFOR»
			
«««			«FOR clazzNode:graph.nodes.sortBy[e|e.elem.name].filter[e|e.elem.EPackage.equals(ePackage)]»
«««			public default «clazzNode.elem.genericType» $(final «clazzNode.elem.name» «clazzNode.elem.name.toFirstLower») {
«««				«clazzNode.elem.genericType» ret = null;
«««				«FOR child: allWithHierarchy»
«««				if(«clazzNode.elem.name.toFirstLower» instanceof «child.name») {
«««					ret = «clazzNode.elem.name.toFirstLower»_«child.name.toFirstLower»((«child.name») «clazzNode.elem.name.toFirstLower»);
«««				}
«««				«ENDFOR»
«««				«IF !clazzNode.elem.abstract»
«««				if(ret == null) ret = «clazzNode.elem.name.toFirstLower»(«clazzNode.elem.name.toFirstLower»);
«««				«ELSE»
«««				«FOR ePp : clazzNode.outgoing.map[cn|cn.elem.EPackage].filter[pa|allDirectPackages.contains(pa)].toSet»
«««				if(ret == null) ret =  «ePp.name».algebra.«ePp.toPackageName».super.$(«clazzNode.elem.name.toFirstLower»);
«««				«ENDFOR»
«««				«ENDIF»
«««				
«««				return ret;
«««			}
			
«««			«ENDFOR»
		}
		'''
	}

	def String processAlgebraOld(EPackage ePackage) {

		val graphCurrentPackage = buildGraph(ePackage)

		val  clusters = calculateClusters(graphCurrentPackage)
		val allTypes = buildAllTypes(clusters)
		
		val allConcretTypes = buildConcretTypes(allTypes)
		
		val allMethods = graphCurrentPackage.nodes.sortBy[e|e.elem.name].filter[e|e.elem.EPackage.equals(ePackage)].filter [e|
			!e.elem.abstract
		]
		
		val allDirectPackages = allMethods.allDirectPackages(ePackage) 

		val all$Types = calculateAllTypes(ePackage, false)
		
		'''
		package «ePackage.name».algebra;
		
		public interface «ePackage.toPackageName»«FOR x : allConcretTypes.keySet BEFORE '<' SEPARATOR ', ' AFTER '>'»«x»«ENDFOR»«FOR ePp : allDirectPackages.sortBy[name].map[x | (x -> buildConcretTypeForParents(x, allTypes))] BEFORE ' extends ' SEPARATOR ', '»«ePp.key.name».algebra.«ePp.key.toPackageName»«FOR x : ePp.value BEFORE '<' SEPARATOR ', ' AFTER '>'»«x»«ENDFOR»«ENDFOR» {
		
			«FOR eClass : allMethods.map[elem]»
				default «eClass.abstractType(allTypes)» «eClass.name.toFirstLower»(final «eClass.javaFullPath» «eClass.name.toFirstLower») {
					throw new UnsupportedOperationException();
				}

			«ENDFOR»
			«FOR abstractTypes : all$Types.entrySet.filter[tmp | tmp.concretTypes(ePackage).size > 0] SEPARATOR '\n'»
			«IF abstractTypes.value.getDirectPackages(ePackage).size > 0»@Override«ENDIF»			
			public default «abstractTypes.key» $(final «abstractTypes.value.findRootType.javaFullPath» «abstractTypes.value.findRootType.name.toFirstLower») {
				final «abstractTypes.key» ret;
				«FOR type : abstractTypes.concretTypes(ePackage).map[elem].sortBy[name] BEFORE 'if' SEPARATOR ' else if' AFTER ''» («abstractTypes.value.findRootType.name.toFirstLower».eClass().getClassifierID() == «abstractTypes.value.findRootType.EPackage.name».«abstractTypes.value.findRootType.EPackage.name.toFirstUpper»Package.«type.name.toUpperSnake») {
					ret = this.«type.name.toFirstLower»((«type.javaFullPath») «abstractTypes.value.findRootType.name.toFirstLower»);
				}«ENDFOR» else {
				«IF abstractTypes.value.getDirectPackages(ePackage).size > 0»
					«abstractTypes.value.getDirectPackages(ePackage).toTryCatch(abstractTypes.value.findRootType.name.toFirstLower)»
				«ELSE»
								«'\t'»throw new RuntimeException("Unknow «abstractTypes.value.findRootType.name» " + «abstractTypes.value.findRootType.name.toFirstLower»);
				«ENDIF»
				}
				return ret;
			}«ENDFOR»
		}'''
	}
	
	private def buildConcretTypes(Map<String, List<GraphNode<EClass>>> allTypes) {
		allTypes.mapValues[x|x.filter[y|!y.elem.abstract]].filter[p1, p2|!p2.empty]
	}
	
	private def List<EPackage> allDirectPackages(Iterable<GraphNode<EClass>> nodes, EPackage ePackage) {
		val allDirectPackagesByInheritance = nodes.getDirectPackageByInheritance(ePackage)
		
		val allDirectPackageByReference = nodes.getAllDirectPackagesByReference(ePackage)
		
		allDirectPackagesByInheritance.addAll(allDirectPackageByReference)
		allDirectPackagesByInheritance.sortBy[name]
	}
	
	private def Set<EPackage> getAllDirectPackagesByReference(Iterable<GraphNode<EClass>> nodes, EPackage ePackage) {
		nodes.map[e|e.elem.EReferences].map[e|e.directlyRelatedTypes].flatten.map[e|e.EPackage].filter [ e |
			!e.equals(ePackage)
		].toSet
	}
	
	private def Set<EPackage> getDirectPackageByInheritance(Iterable<GraphNode<EClass>> nodes, EPackage ePackage) {
		nodes.map[e|e.outgoing].flatten.map[e|e.elem.EPackage].filter [ e |
			!e.equals(ePackage)
		].toSet
	}


	
	private def concretTypes(Entry<String, Iterable<GraphNode<EClass>>> entry, EPackage ePackage) {
		entry.value.findConcretTypes(ePackage).sortWith(new Comparator<GraphNode<EClass>> {
				
				override compare(GraphNode<EClass> o1, GraphNode<EClass> o2) {
					val d1 = o1.distanceFromRoot(entry.value.findRootType)
					val d2 = o2.distanceFromRoot(entry.value.findRootType)
					d1.compareTo(d2)
				}
				
			}).reverse
	}
	
	

	private def String toTryCatch(Iterable<EPackage> packages, String typeVarName) {
			'''
			«IF packages.size == 1»
				ret = «packages.head.name».algebra.«packages.head.toPackageName».super.$(«typeVarName»);
			«ELSE»
				try {
					ret = «packages.head.toPackageName».super.$(«typeVarName»);
				} catch(RuntimeException e) {
					«toTryCatch(packages.tail, typeVarName)»
				}
			«ENDIF»
			'''
	}

	private def Set<GraphNode<EClass>> findConcretTypes(Iterable<GraphNode<EClass>> nodes, EPackage ePackage) {
		nodes.filter[e|!e.elem.abstract].filter[e|e.elem.EPackage.equals(ePackage)].toSet
	}

	private def List<EPackage> getDirectPackages(Iterable<GraphNode<EClass>> entry, EPackage currentPackage) {
		entry.map[outgoing].flatten.map[e|e.elem.EPackage].filter[e|!e.equals(currentPackage)].toSet.sortBy[name]
	}

	public def static EClass getFindRootType(Iterable<GraphNode<EClass>> nodes) {
		val roots = nodes.map[roots].flatten.toSet
		if (roots.size >
			1) {
			throw new RuntimeException('''A classes cluster can't have more than one roots. We find a cluster composed of those roots: «FOR node : roots SEPARATOR ', '»«node.elem.EPackage.name».«node.elem.name»«ENDFOR»''')
		} else {
			roots.head.elem
		}
	}
	
	private def Iterable<GraphNode<EClass>> getListTypesRec(HashSet<EPackage> visited, EPackage ePackage, Graph<EClass> graph, Map<String, List<GraphNode<EClass>>> allTypes) {
		if(!visited.contains(ePackage)) {
			visited.add(ePackage)
			val List<GraphNode<EClass>> relatedToCurrentPackage = graph.nodes.sortBy[e|e.elem.name].filter [e|
				e.elem.EPackage.equals(ePackage) || e.children.exists[f|f.elem.EPackage.equals(ePackage)] || e.elem.EReferences.directlyRelatedTypes.exists[v|v.EPackage.equals(ePackage)]
			].toList
			
			
			val List<EPackage> letgo = relatedToCurrentPackage.allDirectPackages(ePackage)
			letgo.forEach[n|
				relatedToCurrentPackage.addAll(getListTypesRec(visited, n, graph, allTypes))
			] 
			
			relatedToCurrentPackage
			
			
			
		} else {
			newArrayList()
		}
	}

	private def String abstractType(EClass class1, Map<String, List<GraphNode<EClass>>> allTypes) {
		allTypes.entrySet.filter[e|
			e.value.contains(new GraphNode(class1))
		].head.key
	}

	private def void visitPackages(HashSet<EPackage> visitedpackage, EPackage ePackage, Graph<EClass> graph1) {
		visitedpackage.add(ePackage)
		val allEClasses = ePackage.eAllContents.filter[e|e instanceof EClass].map[e|e as EClass].toList.sortBy[e|e.name]
		allEClasses.forEach[e|addParents(graph1, e)]
		allEClasses.forEach[e|e.EReferences.directlyRelatedTypes.forEach[f|addParents(graph1, f)]]

		val notYetVisited = graph1.nodes.sortBy[e|e.elem.name].map[e|e.elem.EPackage].toSet.filter[e|!visitedpackage.contains(e)]
		notYetVisited.forEach [ e |
			visitPackages(visitedpackage, e, graph1)
		]
	}
	
	private def List<EClass> getDirectlyRelatedTypes(EList<EReference> list) {
		list.map[f|f.EType].filter[z|z instanceof EClass].map[q|q as EClass].filter[x|!x.EPackage.name.equals("ecore")].toList
	}

	private def void addParents(Graph<EClass> graph1, EClass e) {
		//println('''# Add class «e.name»''')
		val node = graph1.addNode(e)
		e.ESuperTypes.forEach [ f |
			val node2 = graph1.addNode(f)
			if (!e.root) {
				graph1.addEdge(node, node2)
			}
			addParents(graph1, f)
		]
	}

	/**
	 * A root element is an element with no super type or explicitly defined with @OARoot.
	 */
	private def static boolean isRoot(EClass eClass) {
		eClass.ESuperTypes.empty //|| eClass.hasOARootAnnotation
	}

	private def static EClass findRootParent(EClass eClass) {
		if(eClass.isRoot) eClass else findRootParent(eClass.ESuperTypes.head)
	}

//	private def static boolean hasOARootAnnotation(EClass eClass) {
//		eClass.EAnnotations.exists[e|e.source.equals("OARoot")]
//	}

	private def static String toClassName(String name) {
		name.split("\\.").map[e|e.toFirstUpper].join
	}

	private def static toPackageName(EPackage ePackage) '''«ePackage.name.toClassName»Algebra'''
	
	private def static javaFullPath(EClass eClass) '''«eClass.EPackage.name».«eClass.name»'''
	private def static operationFullPath(EClass eClass, EPackage rootPackage) '''«rootPackage.name».algebra.operation.«rootPackage.name.toFirstUpper»«eClass.name»Operation'''
	
	private def static String toUpperSnake(String name) {
		name.split("(?=\\p{Upper})").map[toUpperCase].join("_").replaceAll("([A-Z])_([A-Z])_", "$1$2")
	}
}
