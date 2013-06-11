// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.TipoJogo;
import br.ufpb.dce.bloomer.core.model.TipoJogoDataOnDemand;
import br.ufpb.dce.bloomer.core.model.TipoJogoIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect TipoJogoIntegrationTest_Roo_IntegrationTest {
    
    declare @type: TipoJogoIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: TipoJogoIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: TipoJogoIntegrationTest: @Transactional;
    
    @Autowired
    TipoJogoDataOnDemand TipoJogoIntegrationTest.dod;
    
    @Test
    public void TipoJogoIntegrationTest.testCountTipoJogoes() {
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to initialize correctly", dod.getRandomTipoJogo());
        long count = TipoJogo.countTipoJogoes();
        Assert.assertTrue("Counter for 'TipoJogo' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void TipoJogoIntegrationTest.testFindTipoJogo() {
        TipoJogo obj = dod.getRandomTipoJogo();
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to provide an identifier", id);
        obj = TipoJogo.findTipoJogo(id);
        Assert.assertNotNull("Find method for 'TipoJogo' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'TipoJogo' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void TipoJogoIntegrationTest.testFindAllTipoJogoes() {
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to initialize correctly", dod.getRandomTipoJogo());
        long count = TipoJogo.countTipoJogoes();
        Assert.assertTrue("Too expensive to perform a find all test for 'TipoJogo', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<TipoJogo> result = TipoJogo.findAllTipoJogoes();
        Assert.assertNotNull("Find all method for 'TipoJogo' illegally returned null", result);
        Assert.assertTrue("Find all method for 'TipoJogo' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void TipoJogoIntegrationTest.testFindTipoJogoEntries() {
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to initialize correctly", dod.getRandomTipoJogo());
        long count = TipoJogo.countTipoJogoes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<TipoJogo> result = TipoJogo.findTipoJogoEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'TipoJogo' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'TipoJogo' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void TipoJogoIntegrationTest.testFlush() {
        TipoJogo obj = dod.getRandomTipoJogo();
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to provide an identifier", id);
        obj = TipoJogo.findTipoJogo(id);
        Assert.assertNotNull("Find method for 'TipoJogo' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyTipoJogo(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'TipoJogo' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void TipoJogoIntegrationTest.testMergeUpdate() {
        TipoJogo obj = dod.getRandomTipoJogo();
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to provide an identifier", id);
        obj = TipoJogo.findTipoJogo(id);
        boolean modified =  dod.modifyTipoJogo(obj);
        Integer currentVersion = obj.getVersion();
        TipoJogo merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'TipoJogo' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void TipoJogoIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to initialize correctly", dod.getRandomTipoJogo());
        TipoJogo obj = dod.getNewTransientTipoJogo(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'TipoJogo' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'TipoJogo' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void TipoJogoIntegrationTest.testRemove() {
        TipoJogo obj = dod.getRandomTipoJogo();
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'TipoJogo' failed to provide an identifier", id);
        obj = TipoJogo.findTipoJogo(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'TipoJogo' with identifier '" + id + "'", TipoJogo.findTipoJogo(id));
    }
    
}
