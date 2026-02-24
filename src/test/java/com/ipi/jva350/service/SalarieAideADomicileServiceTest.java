package com.ipi.jva350.service;

import com.ipi.jva350.exception.SalarieException;
import com.ipi.jva350.model.Entreprise;
import com.ipi.jva350.model.SalarieAideADomicile;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalarieAideADomicileServiceTest {

    @Mock
    private SalarieAideADomicileRepository salarieAideADomicileRepository;

    @InjectMocks
    private SalarieAideADomicileService service;

    @Test
    void calculeLimiteSansMalusBonus() {

        // GIVEN
        // Le repository est mocké
        when(salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1())
                .thenReturn(0.0);

        LocalDate moisEnCours = LocalDate.of(2024, 3, 1);
        LocalDate debutContrat = LocalDate.of(2024, 3, 1);
        LocalDate debut = LocalDate.of(2024, 4, 1);
        LocalDate fin = LocalDate.of(2024, 4, 1);

        double congesN1 = 24;

        // WHEN
        long limite = service.calculeLimiteEntrepriseCongesPermis(
                moisEnCours, congesN1, debutContrat, debut, fin
        );

        // THEN
        double proportion = Entreprise.proportionPondereeDuMois(debut);
        long attendu = Math.round(proportion * congesN1);

        assertEquals(attendu, limite);
    }


    @Test
    void calculeLimiteAvecBonusMalus() {

        // GIVEN
        // Cette fois on active le malus/bonus
        when(salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1())
                .thenReturn(0.5);

        LocalDate moisEnCours = LocalDate.of(2024, 6, 1);
        LocalDate debutContrat = LocalDate.of(2024, 6, 1);
        LocalDate debut = LocalDate.of(2024, 6, 1);
        LocalDate fin = LocalDate.of(2024, 6, 1);

        double congesN1 = 30;

        // WHEN
        long limite = service.calculeLimiteEntrepriseCongesPermis(
                moisEnCours, congesN1, debutContrat, debut, fin
        );

        // THEN
        double proportion = Entreprise.proportionPondereeDuMois(debut);
        double base = proportion * congesN1;

        double proportionMoisEnCours =
                ((6 - Entreprise.getPremierJourAnneeDeConges(moisEnCours).getMonthValue()) % 12) / 12d;

        double diff = proportionMoisEnCours - 0.5;

        // Bonus/malus = 20% de cette différence * congés N-1
        double bonus = diff * 0.2 * congesN1;

        long attendu = Math.round(base + bonus);

        assertEquals(attendu, limite);
    }
}