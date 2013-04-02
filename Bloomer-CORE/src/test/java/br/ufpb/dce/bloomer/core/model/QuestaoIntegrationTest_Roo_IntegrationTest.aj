// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.Questao;
import br.ufpb.dce.bloomer.core.model.QuestaoDataOnDemand;
import br.ufpb.dce.bloomer.core.model.QuestaoIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect QuestaoIntegrationTest_Roo_IntegrationTest {
    
    declare @type: QuestaoIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: QuestaoIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: QuestaoIntegrationTest: @Transactional;
    
    @Autowired
    QuestaoDataOnDemand QuestaoIntegrationTest.dod;
    
    @Test
    public void QuestaoIntegrationTest.testCountQuestaos() {
        Assert.assertNotNull("Data on demand for 'Questao' failed to initialize correctly", dod.getRandomQuestao());
        long count = Questao.countQuestaos();
        Assert.assertTrue("Counter for 'Questao' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void QuestaoIntegrationTest.testFindQuestao() {
        Questao obj = dod.getRandomQuestao();
        Assert.assertNotNull("Data on demand for 'Questao' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Questao' failed to provide an identifier", id);
        obj = Questao.findQuestao(id);
        Assert.assertNotNull("Find method for 'Questao' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Questao' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void QuestaoIntegrationTest.testFindAllQuestaos() {
        Assert.assertNotNull("Data on demand for 'Questao' failed to initialize correctly", dod.getRandomQuestao());
        long count = Questao.countQuestaos();
        Assert.assertTrue("Too expensive to perform a find all test for 'Questao', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Questao> result = Questao.findAllQuestaos();
        Assert.assertNotNull("Find all method for 'Questao' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Questao' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void QuestaoIntegrationTest.testFindQuestaoEntries() {
        Assert.assertNotNull("Data on demand for 'Questao' failed to initialize correctly", dod.getRandomQuestao());
        long count = Questao.countQuestaos();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Questao> result = Questao.findQuestaoEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Questao' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Questao' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void QuestaoIntegrationTest.testFlush() {
        Questao obj = dod.getRandomQuestao();
        Assert.assertNotNull("Data on demand for 'Questao' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Questao' failed to provide an identifier", id);
        obj = Questao.findQuestao(id);
        Assert.assertNotNull("Find method for 'Questao' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyQuestao(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Questao' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void QuestaoIntegrationTest.testMergeUpdate() {
        Questao obj = dod.getRandomQuestao();
        Assert.assertNotNull("Data on demand for 'Questao' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Questao' failed to provide an identifier", id);
        obj = Questao.findQuestao(id);
        boolean modified =  dod.modifyQuestao(obj);
        Integer currentVersion = obj.getVersion();
        Questao merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Questao' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void QuestaoIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Questao' failed to initialize correctly", dod.getRandomQuestao());
        Questao obj = dod.getNewTransientQuestao(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Questao' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Questao' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Questao' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void QuestaoIntegrationTest.testRemove() {
        Questao obj = dod.getRandomQuestao();
        Assert.assertNotNull("Data on demand for 'Questao' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Questao' failed to provide an identifier", id);
        obj = Questao.findQuestao(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Questao' with identifier '" + id + "'", Questao.findQuestao(id));
    }
    
}
