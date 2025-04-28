# Documentación de Integración Continua

## Flujo de trabajo

1. **Compilación**: Se compila el proyecto con Maven en cada push a main o pull request
2. **Pruebas**: Se ejecutan las pruebas unitarias con JUnit
3. **Análisis estático**: SonarQube analiza el código para detectar:
   - Bugs
   - Vulnerabilidades
   - Code smells
   - Cobertura de código

## Resultados

Los resultados de la integración continua se pueden ver en:

- La pestaña "Actions" del repositorio GitHub
- El dashboard de SonarQube para métricas de calidad de código
- Los artefactos generados (reportes de pruebas)

## Requisitos

- Java 21
- Maven 3.6+
- Cuenta en SonarQube/SonarCloud
