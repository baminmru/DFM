# Mapper Project

## Overview

**Mapper** is a Windows Forms application designed to facilitate the mapping and migration of data between source and destination database schemas, with a focus on PostgreSQL. It provides a graphical interface for users to view, link, and manage mappings between source APIs/tables/fields and destination tables/fields, supporting data migration, transformation, and documentation.

## Main Features

- **Visual Mapping Interface:**  
  The main form (`frmMapper`) displays source and destination data structures in grid views, allowing users to create, edit, and delete mappings interactively.
- **PostgreSQL Integration:**  
  Uses the `Npgsql` library to connect to and interact with PostgreSQL databases.
- **Automated View and Table Generation:**  
  Includes utilities (`viewGen`, `PGGen`) to generate SQL views and tables based on mapping definitions and source metadata.
- **Mapping Management:**  
  Supports saving, loading, and editing mapping definitions, including comments and conditions for each mapping.
- **Data Synchronization Scripts:**  
  Includes SQL scripts for updating and synchronizing destination schemas (`update_dest.sql`, `mapper.sql`).
- **API Usage Tracking:**  
  Maintains a list of used APIs (`used_api.sql`) to focus mapping efforts on relevant data sources.

## Project Structure

- **frmMapper.cs / frmMapper.Designer.cs**  
  Main Windows Form for mapping management. Handles UI logic, data loading, and user interactions.
- **pgDataSource.cs**  
  Encapsulates PostgreSQL connection and data access logic using `Npgsql`.
- **viewGen.cs**  
  Generates SQL views and loader scripts for data migration based on source metadata.
- **PGGen.cs**  
  Generates PostgreSQL table creation scripts and mapping scripts from source metadata.
- **Utils.cs**  
  Utility functions for tree manipulation, serialization, string processing, and mapping logic.
- **frmPGLogin.cs**  
  Login form for establishing a PostgreSQL connection.
- **SQL Scripts**  
  - `mapper.sql`: Defines core tables (`dest_data`, `src_data`, `map_data`, `used_api`) and indexes.
  - `update_dest.sql`: Scripts for updating and synchronizing destination schema metadata.
  - `used_api.sql`: Example insert statements for tracking used APIs.

## Usage

1. **Connect to PostgreSQL:**  
   Launch the application and use the login form to connect to your PostgreSQL database.
2. **View and Map Data:**  
   The main form displays source and destination schemas. Select fields and create mappings as needed.
3. **Generate Scripts:**  
   Use menu options to generate SQL views, tables, and mapping scripts for migration.
4. **Synchronize Schemas:**  
   Use the provided SQL scripts to update and synchronize your destination schema as required.

## Dependencies

- [.NET 6.0 Windows](https://dotnet.microsoft.com/en-us/download/dotnet/6.0)
- [Npgsql 7.0.10](https://www.npgsql.org/) (PostgreSQL data provider for .NET)
- Standard .NET libraries for Windows Forms, Data, XML, and JSON processing

## Build Instructions

1. **Requirements:**  
   - Windows OS  
   - .NET 6.0 SDK or later
2. **Build:**  
   Open `mapper.sln` in Visual Studio or run:
   ```
   dotnet build
   ```
3. **Run:**  
   ```
   dotnet run --project mapper.csproj
   ```

## Database Schema

The core tables managed by the application are:

- **dest_data:** Destination schema metadata (table, field, type, comment, key).
- **src_data:** Source schema metadata (table, field, type, comment, API, etc.).
- **map_data:** Mapping definitions between source and destination fields.
- **used_api:** List of APIs in use for focused mapping.

See `mapper.sql` and `update_dest.sql` for full schema and update scripts.

## Authors & License

- **Author:** _[Your Name Here]_
- **License:** _[Specify License, e.g., MIT]_

## Additional Notes

- The project is extensible for other data sources and mapping scenarios.
- Utility classes (`Utils.cs`) provide reusable methods for serialization, string handling, and mapping logic.
- The application is designed for technical users familiar with database schemas and migration processes. 