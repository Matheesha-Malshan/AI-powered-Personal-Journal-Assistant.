package com.intelligentDiary.controller;

import com.intelligentDiary.service.entry.EntryService;
import com.intelligentDiary.service.entry.TextEntryTypeService;
import lombok.RequiredArgsConstructor;
import com.intelligentDiary.model.InputEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RequiredArgsConstructor
@RestController
@RequestMapping("/text")
public class TextController {

    final TextEntryTypeService entryTypeService;

    @PostMapping("/input-type")
    public DeferredResult<ResponseEntity<String>>inputType(@RequestBody InputEntry inputEntry){


        DeferredResult<ResponseEntity<String>> deferredResult=new DeferredResult<>(30000L);

        Thread.startVirtualThread(() -> {
            try {
                entryTypeService.createEntry(inputEntry); // your existing method
                deferredResult.setResult(ResponseEntity.ok("Entry created successfully"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                deferredResult.setErrorResult(ResponseEntity.status(500).body("Interrupted: " + e.getMessage()));
            } catch (Exception e) {
                deferredResult.setErrorResult(ResponseEntity.status(500).body("Error: " + e.getMessage()));
            }
        });

        return deferredResult;

    }
    @GetMapping("/search-by-text/{userId}/{query}")
    public void searchText(@PathVariable String userId,@PathVariable String query){
        entryTypeService.searchByText(userId,query);


    }




}
