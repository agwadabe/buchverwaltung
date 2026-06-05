# Deployment auf Render

Dieses Projekt ist fuer Render als Docker Web Service vorbereitet.

## Deployment

1. Code in ein GitHub/GitLab/Bitbucket Repository pushen.
2. In Render `New` > `Blueprint` auswaehlen.
3. Repository verbinden.
4. Render erkennt `render.yaml` und erstellt den Web Service `buchverwaltung`.

Alternativ kann ein einzelner Web Service manuell erstellt werden:

- Runtime: `Docker`
- Dockerfile Path: `./Dockerfile`
- Region: `Frankfurt`
- Environment Variable: `PORT=10000`

Die Anwendung nutzt lokal weiterhin Port `8080`. Auf Render wird automatisch der Wert aus `PORT` verwendet.
