/*
 * generated by Xtext 2.14.0
 */
package imp.lang.xtext.tests

import com.google.inject.Inject
import imp.model.imp.Stmt
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(ImpInjectorProvider)
class ImpParsingTest {
	@Inject
	ParseHelper<Stmt> parseHelper
	
	@Test
	def void loadModel() {
		val result = parseHelper.parse('''
			Hello Xtext!
		''')
		Assert.assertNotNull(result)
		val errors = result.eResource.errors
		Assert.assertTrue('''Unexpected errors: «errors.join(", ")»''', errors.isEmpty)
	}
}
