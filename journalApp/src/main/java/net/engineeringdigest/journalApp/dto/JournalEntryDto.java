package net.engineeringdigest.journalApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.engineeringdigest.journalApp.enums.Sentiment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntryDto {
    @NonNull
    private String title;
    private String content;
    private Sentiment sentiment;
}
