package logo.lang.tests

import org.junit.jupiter.api.DynamicTest
import java.io.File
import org.eclipse.emf.ecoretools.ale.compiler.lib.LogService
import org.apache.commons.io.FileUtils
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import org.junit.jupiter.api.Assertions

abstract class AbstractLogoTest {
	
	val langname = 'logo'

	def Iterable<DynamicTest> test0() {

		val root = new File('''../«langname».lang.tests/programs/''')

		root.listFiles
			.filter[it.isFile]
			.filter[it.name.endsWith('''.«langname».xmi''')]
			.map [
				DynamicTest.dynamicTest(it.name, [
					genericTest(it.absolutePath, FileUtils.readFileToString(new File(it.absolutePath + '.expected')))
				])
			]
	}

	def genericTest(String file, String expectedResult) {
		LogService.TOSDTOUT = false

		Resource.Factory.Registry.INSTANCE.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl)
		val resSet = new ResourceSetImpl
		val createFileURI = URI.createFileURI(file);
		val resource = resSet.getResource(createFileURI, true)
		this.execute(resource)

		Assertions.assertEquals(expectedResult, LogService.sb.toString)
	}

	abstract def void execute(Resource resource)
}