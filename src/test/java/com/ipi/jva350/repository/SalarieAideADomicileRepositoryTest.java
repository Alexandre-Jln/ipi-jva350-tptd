package com.ipi.jva350.repository;

import com.ipi.jva350.model.SalarieAideADomicile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SalarieAideADomicileRepositoryTest {

    @Autowired
    private SalarieAideADomicileRepository salarieAideADomicileRepository;

    @BeforeEach
    public void before(){
        salarieAideADomicileRepository.deleteAll();
        SalarieAideADomicile jeanToutLeTemps = new SalarieAideADomicile();
        jeanToutLeTemps.setNom("Jean");
        salarieAideADomicileRepository.save(jeanToutLeTemps);
    }

    @Test
    void testFindByNomPasPresent(){
        // Given
        // When
        SalarieAideADomicile resultat = salarieAideADomicileRepository.findByNom("Inconnu");
        // Then
        Assertions.assertNull(resultat);
    }

    @Test
    void testFindByNomPresent(){
        // Given
        // When
        SalarieAideADomicile jeanRes = salarieAideADomicileRepository.findByNom("Jean");
        // Then
        Assertions.assertNotNull(jeanRes);
        Assertions.assertEquals("Jean", jeanRes.getNom());
    }
}