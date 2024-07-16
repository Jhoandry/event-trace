package com.prewave.eventTrace.services.implementation.definitions;

import com.prewave.eventTrace.repositories.models.Content;
import com.prewave.eventTrace.repositories.models.Event;
import com.prewave.eventTrace.repositories.models.QueryTerm;
import com.prewave.eventTrace.services.integration.dto.AlertDto;
import com.prewave.eventTrace.services.integration.dto.ContentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AlertEventExtractorTest {

    @Test
    @DisplayName("Extract match with empty Event list")
    public void extractMatches() {
        // Given
        List<AlertDto> alerts = List.of(
                new AlertDto("alert-1", List.of(
                        new ContentDto("MÜNCHEN (dpa-AFX) - Die IG Metall erwartet einen heißen Herbst mit vielen Auseinandersetzungen um Jobs in Deutschland. Es wird um viele Arbeitsplätze gehen, sagte Vorstandsmitglied und Hauptkassierer Jürgen Kerner am Mittwochabend in München. Alleine in den Branchen, für die die Gewerkschaft zuständig sei, stünden ungefähr 300 000 Jobs im Feuer. Der größte Bereich dabei seien die Automobilindustrie und deren Zulieferer, doch auch anderen Branchen seien betroffen.", "short", "de")
                ), LocalDateTime.now(), "tweet"),
                new AlertDto("alert-2", List.of(
                        new ContentDto("Sales Punching faking sales numbers Hey, SEC, look at Tesla", "text", "en"),
                        new ContentDto("Tesla Full Self Driving: New update now drives thru Green Lights and auto stops at Red Lights and Stop Signs without interaction.", "text", "en")
                ), LocalDateTime.now(), "link")
        );

        List<QueryTerm> queryTerms = List.of(
                new QueryTerm(101, "IG Metall",true, "de"),
                new QueryTerm(601, "Tesla", false, "en")
        );

        // When
        List<Event> result = AlertMatchExtractor.extractMatches(alerts, queryTerms, List.of());

        // Then
        assertEquals(2, result.size());

        //Checking matches
        Optional<Event> eventMatch1 = result.stream().filter(event -> event.getQueryTermId() == 101 && event.getAlertId().equals("alert-1")).findFirst();
        assertTrue(eventMatch1.isPresent());
        assertEquals(1, eventMatch1.get().getContents().size());
        assertEquals(1, eventMatch1.get().getContents().getFirst().getMatches());

        Optional<Event> eventMatch2 = result.stream().filter(event -> event.getQueryTermId() == 601 && event.getAlertId().equals("alert-2")).findFirst();
        assertTrue(eventMatch2.isPresent());
        assertEquals(2, eventMatch2.get().getContents().size());
        assertEquals(1, eventMatch2.get().getContents().getFirst().getMatches());
    }

    @Test
    @DisplayName("Extract Matches - Keep Order True")
    public void extractMatchesKeepOrderNoExistingMatches() {
        // Given
        List<AlertDto> alerts = List.of(
                new AlertDto("alert-1",List.of(
                        new ContentDto( "Wolfgang Lemb, ig metall Germany stands in solidarity with #StrikeForBlackLives", "short", "de")
                ), LocalDateTime.now(), "link")
        );

        List<QueryTerm> queryTerms = List.of(
                new QueryTerm(101, "IG Metall", true, "de")
        );

        // When
        List<Event> result = AlertMatchExtractor.extractMatches(alerts, queryTerms, List.of());

        // Then
        assertEquals(1, result.size());

        //Checking matches
        Optional<Event> eventMatch = result.stream().filter(event -> event.getQueryTermId() == 101 && event.getAlertId().equals("alert-1")).findFirst();
        assertTrue(eventMatch.isPresent());
        assertEquals(1, eventMatch.get().getContents().size());
        assertEquals(1, eventMatch.get().getContents().getFirst().getMatches());
    }

    @Test
    @DisplayName("Extract Matches - Keep Order False")
    public void extractMatchesNoKeepOrderAndExistingMatches() {
        // Given
        List<AlertDto> alerts = List.of(
                new AlertDto("alert-1", List.of(
                        new ContentDto("Tesla Full Self Driving: New update now drives thru Green Lights and auto stops at Red Lights and Stop Signs without interaction.", "text", "en")
                ), LocalDateTime.now(), "link")
        );

        List<QueryTerm> queryTerms = List.of(
                new QueryTerm(601, "drives tesla", false, "en")
        );

        // When
        List<Event> result = AlertMatchExtractor.extractMatches(alerts, queryTerms, List.of());

        // Then
        assertEquals(1, result.size());

        //Checking matches
        Optional<Event> eventMatch = result.stream().filter(event -> event.getQueryTermId() == 601 && event.getAlertId().equals("alert-1")).findFirst();
        assertTrue(eventMatch.isPresent());
        assertEquals(1, eventMatch.get().getContents().size());
        assertEquals(2, eventMatch.get().getContents().getFirst().getMatches());
    }

    @Test
    @DisplayName("Extract Matches - with existing events")
    void testWithExistingEvents() {
        List<AlertDto> alerts = List.of(
                new AlertDto("alert-1", List.of(
                        new ContentDto("@En1Buena Ojalá lo logre eh, y una oferta de Ferrari es inigualable pero la veo fea", "text", "es"),
                        new ContentDto("Arbeitsplatz in danger", "text", "de")
                ), LocalDateTime.now(), "link")
        );

        List<QueryTerm> queryTerms = List.of(
                new QueryTerm(101, "IG oferta", false, "es"),
                new QueryTerm(201, "Arbeitsplatz", true, "de")
        );

        List<Event> existingEvents = List.of(
                new Event(201, "alert-1", List.of(
                        new Content("Arbeitsplatz in danger", "de", 1)
                ))
        );

        List<Event> result = AlertMatchExtractor.extractMatches(alerts, queryTerms, existingEvents);

        // Verify the results
        assertEquals(2, result.size());

        //Checking matches
        Optional<Event> eventMatch1 = result.stream().filter(event -> event.getQueryTermId() == 101 && event.getAlertId().equals("alert-1")).findFirst();
        assertTrue(eventMatch1.isPresent());
        assertEquals(1, eventMatch1.get().getContents().size());
        assertEquals(2, eventMatch1.get().getContents().getFirst().getMatches());

        Optional<Event> eventMatch2 = result.stream().filter(event -> event.getQueryTermId() == 201 && event.getAlertId().equals("alert-1")).findFirst();
        assertTrue(eventMatch2.isPresent());
        assertEquals(1, eventMatch2.get().getContents().size());
        assertEquals(2, eventMatch2.get().getContents().getFirst().getMatches());
    }
}
