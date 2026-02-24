package com.ipi.jva350.model;

import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import com.ipi.jva350.service.SalarieAideADomicileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@ExtendWith(MockitoExtension.class)
public class SalarieAideADomicileTest {

    @Mock
    private SalarieAideADomicileRepository salarieAideADomicileRepository;

    @InjectMocks
    private SalarieAideADomicileService salarieAideADomicileService;

    @Test
    void naPasDroitQuandJoursInferieurA10() {
        // GIVEN
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        salarie.setJoursTravaillesAnneeNMoins1(9);
        // WHEN
        boolean resultat = salarie.aLegalementDroitADesCongesPayes();
        // THEN
        Assertions.assertFalse(resultat);
    }

    @Test
    void naPasDroitQuandJoursEgalA10() {
        // GIVEN
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        salarie.setJoursTravaillesAnneeNMoins1(10);
        // WHEN
        boolean resultat = salarie.aLegalementDroitADesCongesPayes();
        // THEN
        Assertions.assertTrue(resultat);
    }

    @Test
    void aDroitQuandJoursSuperieurA10() {
        // GIVEN
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        salarie.setJoursTravaillesAnneeNMoins1(11);
        // WHEN
        boolean resultat = salarie.aLegalementDroitADesCongesPayes();
        // THEN
        Assertions.assertTrue(resultat);
    }

    @ParameterizedTest(name = "plage de congés {0}-{1} n'a pas le bon nombre de jours : {2}")
    @CsvSource({
            "'2026-08-01', '2026-08-21', 17",
            "'2025-12-22', '2026-01-05', 11",
            "'2025-11-11', '2025-11-11', 0",
    })
    public void testCalculeJoursDeCongeDecomptesPourPlage(String dateDebutString, String dateFinString, int nbJoursDeConges) {
        // Given
        SalarieAideADomicile monSalarie = new SalarieAideADomicile();
        LocalDate dateDebut = LocalDate.parse(dateDebutString);
        LocalDate dateFin = LocalDate.parse(dateFinString);
        // When
        LinkedHashSet<LocalDate> res = monSalarie.calculeJoursDeCongeDecomptesPourPlage(dateDebut, dateFin);
        // Then
        Assertions.assertEquals(nbJoursDeConges, res.size());
    }

    // Mock test - vérifie qu'un salarié sans droit aux congés lève une exception
    @Test
    public void testAjouteConge_SansCongesPayes_LeveException() {
        // Given
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        salarie.setJoursTravaillesAnneeNMoins1(0);
        salarie.setCongesPayesAcquisAnneeNMoins1(0);
        salarie.setMoisEnCours(LocalDate.of(2025, 3, 1));
        salarie.setCongesPayesPris(new LinkedHashSet<>());

        // When / Then
        Assertions.assertThrows(Exception.class, () -> {
            salarieAideADomicileService.ajouteConge(
                    salarie,
                    LocalDate.of(2025, 3, 10),
                    LocalDate.of(2025, 3, 14)
            );
        });
    }
}