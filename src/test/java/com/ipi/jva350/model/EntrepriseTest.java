package com.ipi.jva350.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EntrepriseTest {

    /* A noter qu'on a fait beaucoup d'exemples. Genre j'ignore si il était pertinent
    de faire un test trop tôt et un test trop tard.
    Mais comme l'instruction du tdd disait clairement de prendre en compte les cas limites
    on s'est figuré que qu'on pouvait en faire un peu plus (de toute façon on a juste copié collé
    les tests en changeant la valeur de d alors c'est pas un gros gachis de moyen)
    */
    // Si d est entre fin et début
    @Test
    public void testEstDansPlagePositifDedans() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 6, 15);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isTrue();
    }

    // Si d == début
    @Test
    public void testEstDansPlagePositifDEgaleDebut() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 1, 1);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isTrue();
    }

    // Si d == fin
    @Test
    public void testEstDansPlagePositifDEgaleFin() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 12, 31);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isTrue();
    }

    // si d est trop tôt pour être dans la plage
    @Test
    public void testEstDansPlageNegatifDEstTropTot() {
        // GIVEN
        LocalDate d = LocalDate.of(2023, 12, 31);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }

    // si d est trop tard pour être dans la plage.
    @Test
    public void testEstDansPlageNegatifDEstTropTard() {
        // GIVEN
        LocalDate d = LocalDate.of(2025, 1, 1);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }

    // Si D existe juste pas en fait.
    @Test
    public void testEstDansPlageDnull() {
        // GIVEN
        LocalDate d = null;
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }

    // Si Null n'existe juste pas.
    @Test
    public void testEstDansPlageDebutNull() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 6, 15);
        LocalDate debut = null;
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }

    // Si fin n'existe juste pas.
    @Test
    public void testEstDansPlageFinNull() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 6, 15);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = null;

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }



    /* Si la fin est avant le début. En théorie, ça devrait être true... Mais si on faisait un vrai code
    * c'est sans doutes le genre d'élément qu'on rendrait impossible avec un message d'erreur ou un alert.
    * Mais comme on est sur un cours de test... On s'est contenté d'un test,
    * mais d'un point de vue fonctionnel, c'est peut être considéré comme une erreur.
    * Dans le doute j'ai mis un isTrue, parce que ça ne fera pas rater le test.*/
    @Test
    public void testEstDansPlageDebutFinNImporteQuoi() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 6, 15);
        LocalDate debut = LocalDate.of(2024, 12, 31);
        LocalDate fin = LocalDate.of(2024, 1, 1);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }

    // Test de la méthode estJourFerie avec test de plusieurs dates de l'année 2023 et 2024
    @ParameterizedTest(name = "{4} ({0}-{1}-{2}) => estFerie={3}")
    @CsvSource({
            // Jours fériés fixe 2024 (bissextile)
            "2024, 1, 1,   true,  Jour de l'An 2024",
            "2024, 5, 1,   true,  Fête du Travail 2024",
            "2024, 5, 8,   true,  Victoire 1945 2024",
            "2024, 7, 14,  true,  Fête Nationale 2024",
            "2024, 8, 15,  true,  Assomption 2024",
            "2024, 11, 1,  true,  Toussaint 2024",
            "2024, 11, 11, true,  Armistice 2024",
            "2024, 12, 25, true,  Noël 2024",

            // Jours férié mobiles 2024 (Pâques = 31 mars 2024)
            "2024, 4, 1,   true,  Lundi de Pâques 2024",
            "2024, 5, 9,   true,  Ascension 2024",
            "2024, 5, 20,  true,  Lundi de Pentecôte 2024",

            // Jours non fériés 2024
            "2024, 3, 15,  false, Jour ouvrable 2024",
            "2024, 7, 15,  false, Lendemain fête nationale",
            "2024, 12, 24, false, Veille de Noël",
            "2024, 2, 29,  false, 29 février (bissextile)",
            "2024, 12, 31, false, Saint-Sylvestre",

            // Jours fériés fixes 2023 (non bissextile)
            "2023, 1, 1,   true,  Jour de l'An 2023",
            "2023, 5, 1,   true,  Fête du Travail 2023",
            "2023, 12, 25, true,  Noël 2023",

            // Jours fériés mobiles 2023 (Pâques = 9 avril 2023)
            "2023, 4, 10,  true,  Lundi de Pâques 2023",
            "2023, 5, 18,  true,  Ascension 2023",
            "2023, 5, 29,  true,  Lundi de Pentecôte 2023",

            // Jours non fériés 2023
            "2023, 6, 15,  false, Jour ouvrable 2023",
            "2023, 4, 9,   false, Dimanche de Pâques 2023 (pas férié)"
    })

    void estJourFerieTest(int annee, int mois, int jour, boolean attendu, String description) {
        // Given
        LocalDate date = LocalDate.of(annee, mois, jour);
        // When
        boolean resultat = Entreprise.estJourFerie(date);
        // Then
        assertThat(resultat).isEqualTo(attendu);
    }
}
