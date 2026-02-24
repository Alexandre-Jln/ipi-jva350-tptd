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
        // "Jean" existe déjà grâce au @BeforeEach
        // When
        SalarieAideADomicile jeanRes = salarieAideADomicileRepository.findByNom("Jean");
        // Then
        Assertions.assertNotNull(jeanRes);
        Assertions.assertEquals("Jean", jeanRes.getNom());
    }

    @Test
    void testPartCongesPrisTotauxAnneeNMoins1_AvecSalaries() {
        // Given
        SalarieAideADomicile jean = salarieAideADomicileRepository.findByNom("Jean");
        jean.setCongesPayesPrisAnneeNMoins1(5.0);
        jean.setCongesPayesAcquisAnneeNMoins1(20.0);
        salarieAideADomicileRepository.save(jean);

        SalarieAideADomicile marie = new SalarieAideADomicile();
        marie.setNom("Marie");
        marie.setCongesPayesPrisAnneeNMoins1(10.0);
        marie.setCongesPayesAcquisAnneeNMoins1(30.0);
        salarieAideADomicileRepository.save(marie);

        // When
        // sum(pris) = 5 + 10 = 15, sum(acquis) = 20 + 30 = 50 => 15/50 = 0.3
        Double resultat = salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1();

        // Then
        Assertions.assertNotNull(resultat);
        Assertions.assertEquals(0.3, resultat, 0.001);
    }

    @Test
    void testPartCongesPrisTotauxAnneeNMoins1_AucunConges() {
        // Given
        // Jean a des congés acquis mais n'en a pris aucun
        SalarieAideADomicile jean = salarieAideADomicileRepository.findByNom("Jean");
        jean.setCongesPayesPrisAnneeNMoins1(0);
        jean.setCongesPayesAcquisAnneeNMoins1(20.0);
        salarieAideADomicileRepository.save(jean);

        // When
        // sum(pris) = 0, sum(acquis) = 20 => 0/20 = 0.0
        Double resultat = salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1();

        // Then
        Assertions.assertNotNull(resultat);
        Assertions.assertEquals(0.0, resultat, 0.001);
    }

    @Test
    void testPartCongesPrisTotauxAnneeNMoins1_TousCongesPris() {
        // Given
        SalarieAideADomicile jean = salarieAideADomicileRepository.findByNom("Jean");
        jean.setCongesPayesPrisAnneeNMoins1(20.0);
        jean.setCongesPayesAcquisAnneeNMoins1(20.0);
        salarieAideADomicileRepository.save(jean);

        // When
        // 20/20 = 1.0
        Double resultat = salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1();

        // Then
        Assertions.assertNotNull(resultat);
        Assertions.assertEquals(1.0, resultat, 0.001);
    }

    @Test
    void testPartCongesPrisTotauxAnneeNMoins1_BaseVide() {
        // Given
        salarieAideADomicileRepository.deleteAll();

        // When
        Double resultat = salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1();

        // Then
        // Aucun salarié => sum retourne null
        Assertions.assertNull(resultat);
    }
}