package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.ejbtransformations.java2pcm.EJBJava2PCMTransformationsTestSuite
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.ejbtransformations.seffstatements.EJBJava2PCMSeffTestSuite

@RunWith(Suite)
@SuiteClasses(#[
	EJBJava2PCMTransformationsTestSuite,
	EJBJava2PCMSeffTestSuite
])
class PcmJavaEjbTransformationsTestSuite {
	
}