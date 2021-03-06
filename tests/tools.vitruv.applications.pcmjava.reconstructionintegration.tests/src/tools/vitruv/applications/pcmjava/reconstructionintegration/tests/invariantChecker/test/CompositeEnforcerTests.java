package tools.vitruv.applications.pcmjava.reconstructionintegration.tests.invariantChecker.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.InvariantEnforcerFacadeBuilder;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.PcmRepositoryElementSelector;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PcmtoJavaBeginChar;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PcmToJavaComponentInterfaceImplementsAmbiguity;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PcmToJavaKeywords;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PcmToJavaSameIdentifier;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PcmToJavaSpecialChars;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PcmToJavaVitruviusKeywords;
import tools.vitruv.applications.pcmjava.reconstructionintegration.invariantcheckers.pcmjamoppenforcer.PcmToJavaWhiteSpace;
import tools.vitruv.domains.pcm.util.RepositoryModelLoader;
import tools.vitruv.extensions.constructionsimulation.invariantcheckers.InvariantEnforcer;

/**
 * This class covers tests that use several invariant enforcers for one model.
 *
 */
public class CompositeEnforcerTests {
    private static File locallog;
    private static WriterAppender wa;

    /**
     * Sets the up before class.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Tear down after class.
     *
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception {
        // final Logger logger = LogManager.getLogger(InvariantEnforcer.class); 20.03.15: old
        // version, not in use, since rootlooger is used
        // FileOutputStream fos = new FileOutputStream(new
        // File("Testmodels/invariantTests/testlog"));

        final Logger logger = LogManager.getRootLogger();
        locallog = new File("testlog");
        final FileWriter fw = new FileWriter(locallog);

        // TODO review logging functionality for all (sub)classes
        logger.setLevel(Level.INFO);
        wa = new WriterAppender();
        wa.setWriter(fw);
        wa.setLayout(new PatternLayout("%-5p [%t]: %m%n"));
        logger.addAppender(wa);
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception {
        locallog.delete();
        final Logger logger = LogManager.getRootLogger();
        wa.close();
        logger.removeAppender(wa);

    }

    /**
     * The allViolations.repository model has 17 errors that should be detected and fixed by the
     * enforcers below. Checks not how the errors were fixed, only if something has been done.
     */
    @Test
    public void allEnforcerTest() {
        final String path = "Testmodels/invariantTests/allViolations.repository";
        Resource model = RepositoryModelLoader.loadPcmResource(path);

        final List<InvariantEnforcer> enforcers = new ArrayList<>();
        enforcers.add(new PcmToJavaComponentInterfaceImplementsAmbiguity());

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PcmRepositoryElementSelector(),
                new PcmToJavaKeywords()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PcmRepositoryElementSelector(),
                new PcmToJavaSpecialChars()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PcmRepositoryElementSelector(),
                new PcmToJavaVitruviusKeywords()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PcmRepositoryElementSelector(),
                new PcmToJavaWhiteSpace()));

        enforcers.add(InvariantEnforcerFacadeBuilder.buildInvariantEnforcerFacade(new PcmRepositoryElementSelector(),
                new PcmtoJavaBeginChar()));

        enforcers.add(new PcmToJavaSameIdentifier());

        for (final InvariantEnforcer enf : enforcers) {
            model = enf.loadEnforceReturn(model);
        }

        final String tmpURI = model.getURI().toFileString().replaceFirst("allViolations", "allViolationsMOD");
        final URI fileURI = URI.createFileURI(new File(tmpURI).getAbsolutePath());

        model.setURI(fileURI);
        final int ctr = this.checkAmountOfChanges(model);

        assertTrue(ctr == 17);
    }

    /**
     * Check amount of changes.
     *
     * @param rMod
     *            the r mod
     * @return the int
     */
    private int checkAmountOfChanges(final Resource rMod) {
        try {
            rMod.save(null);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        int ctr = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(locallog))) {
            while (br.readLine() != null) {
                ctr++;
            }
        } catch (final IOException e) {

            e.printStackTrace();
        }
        return ctr;
    }

}
