package com.spring.Journal.Service;

import com.spring.Journal.Controller.Repository.JournalEntryRepository;
import com.spring.Journal.Controller.Repository.UserRepository;
import com.spring.Journal.Entity.JournalEntry;
import com.spring.Journal.Entity.User;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class JournalEntryServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    JournalEntryService journalEntryService;

    @Mock
    JournalEntryRepository journalEntryRepository;

    @Mock
    UserRepository userRepository;


    @Test
    void saveEntry() {

        // Mock user
        User mockUser = new User("anant","1234");
        mockUser.setRoles(Arrays.asList("USER"));

        // Journal entry to save
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setName("Test1");
        journalEntry.setContent("This is Test");

        // Tell UserService to return mock user
        when(userService.findByUserName("anant")).thenReturn(mockUser);

        // Mock journal entry save operation
        when(journalEntryRepository.save(journalEntry)).thenReturn(journalEntry);

        // Since saveUser() is a void method, use Mockito’s doNothing() or doAnswer() instead.
        doNothing().when(userService).saveUser(mockUser);

        journalEntryService.saveEntry(journalEntry,"anant");

        assertEquals(1, mockUser.getJournalEntries().size());
        assertEquals("Test1", mockUser.getJournalEntries().get(0).getName());
        assertEquals("This is Test", mockUser.getJournalEntries().get(0).getContent());

    }

    @Test
    void getAllEntry() {
    }

    @Test
    void findByEntryId_shouldReturnEntry() {
        // Arrange
        ObjectId id = new ObjectId();
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setId(id);

        when(journalEntryRepository.findById(id))
                .thenReturn(Optional.of(journalEntry));

        // Act
        Optional<JournalEntry> result = journalEntryService.findByEntryId(id);

        // Assert
        assertTrue(result.isPresent(), "Journal entry should be found");
        assertEquals(id, result.get().getId());
    }


    @Test
    void findByEntryName() {
    }

    @Test
    void deleteByEntryTest() {
        // Mock user
        User mockUser = new User("anant","1234");
        mockUser.setRoles(Arrays.asList("USER"));

        ObjectId id = new ObjectId();
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setId(id);

        // Tell UserService to return mock user
        when(userService.findByUserName("anant")).thenReturn(mockUser);

        // Add entry to user's list so removeIf() will delete it
        mockUser.setJournalEntries(new ArrayList<>(List.of(journalEntry)));

        boolean check = journalEntryService.deleteByEntry(id,mockUser.getUserName());

        assertTrue(check,"Entry should have been deleted but it wasn't");

        // Verify side effects
        verify(userService, times(1)).saveUser(mockUser);
        verify(journalEntryRepository, times(1)).deleteById(id);

    }
}