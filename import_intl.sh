#!/bin/bash
dart --package-root=./src/main/dart/GromTable/packages  /Users/roman/Documents/bleeding_edge/dart/pkg/intl/test/message_extraction/generate_from_json.dart --output-dir=./src/main/dart/GromTable/web/ ./src/main/dart/GromTable/web/*.dart ./intl/intl_messages_uk.json ./intl/intl_messages_ru.json ./intl/intl_messages_en.json
