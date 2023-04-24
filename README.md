# Traballo tutelado O/RM

> Juan Toirán Freire \<juan.tfreire@udc.es\>

> Iker García Calviño \<iker.gcalvino@udc.es\>

## Diagrama de clases

![](/doc/diagrama.png)

## Relación de eleccións realizadas

| Elección a realizar                                    | Elección |
|:-------------------------------------------------------|:---------|
| Estratexia para a xerarquía                            |          |
| Asociación implementada como bidireccional:            |          |
| Propiedades/coleccións con configuración EAGER         |          |
| Propiedade/colección LAZY con método de inicialización |          |
| Propagación automática de operacións                   |          |

## Relación de métodos implementados

| Método para implementar                         | Implementación |
|:------------------------------------------------|:---------------|
| MO2.2 Métodos de conveniencia                   |                |
| MO4.1 Métodos de recuperación por clave natural |                |
| MO4.2 Métodos de alta                           |                |
| MO4.3 Métodos de eliminación                    |                |
| MO4.4 Métodos de modificación                   |                |
| MO4.5 Consulta JPQL con INNER JOIN              |                |
| MO4.5 Consulta JPQL con OUTER JOIN              |                |
| MO4.5 Consulta JPQL con subconsulta             |                |
| MO4.5 Consulta JPQL con función de agregación   |                |
| MO4.6 Método para inicialización de prop. LAZY  |                |

## Relación de tests implementados

| Tests para implementar            | Implementación |
|:----------------------------------|:---------------|
| Test01 Recuperación clave natural |                |
| Test02 Métodos de alta            |                |
| Test03 Métodos de eliminación     |                |
| Test04 Métodos de modificación    |                |
| Test05 Propagación                |                |
| Test06 JPQL                       |                |
| Test07 Carga EAGER                |                |
| Test08 Inicialización LAZY        |                |
