package com.example.starwarsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.starwarsapp.R
import com.example.starwarsapp.ui.viewmodel.CharacterViewModel
import com.example.starwarsapp.ui.components.CharacterCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val characters = viewModel.characters
    val loading = viewModel.isLoading
    val error = viewModel.errorMessage

    var searchQuery by remember { mutableStateOf("") }
    var selectedLetter by remember { mutableStateOf<Char?>(null) }
    var showFilter by remember { mutableStateOf(false) }

    val filteredCharacters = characters.filter { character ->
        (searchQuery.isEmpty() || character.name.contains(searchQuery, ignoreCase = true)) &&
                (selectedLetter == null || character.name.startsWith(selectedLetter!!, ignoreCase = true))
    }

    Column(modifier = modifier.fillMaxSize()) {

        // Header: Back Button + Title + Filter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.title_characters),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = stringResource(R.string.filter_search),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { showFilter = !showFilter }
            )
        }

        // Filter Panel
        if (showFilter) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text(stringResource(R.string.search_by_name)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ('A'..'Z').forEach { letter ->
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    if (selectedLetter == letter)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    selectedLetter =
                                        if (selectedLetter == letter) null else letter
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = letter.toString(),
                                color =
                                    if (selectedLetter == letter)
                                        MaterialTheme.colorScheme.onPrimary
                                    else
                                        MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Content
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                stringResource(R.string.loading_characters),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }

                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = error, color = MaterialTheme.colorScheme.error)
                    }
                }

                filteredCharacters.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.no_characters_found),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                    ) {
                        items(filteredCharacters) { character ->
                            CharacterCard(character)
                        }
                    }
                }
            }
        }
    }
}