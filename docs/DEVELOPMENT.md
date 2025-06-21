# Guía de Desarrollo

## Nombres de Variables

Las variables deben seguir el estilo `lowerCamelCase`. Aquí hay algunos ejemplos:

```java
int playerScore;
String gameTitle;
boolean isGameOver;
```

Además, en Java se deben seguir los siguientes estándares para nombrar otros elementos:
- **Clases**: Utilizar el estilo `UpperCamelCase`. Ejemplo:
  ```java
  public class GameEngine {}
  public class PlayerStats {}
  ```

- **Métodos**: Utilizar el estilo `lowerCamelCase`. Ejemplo:
  ```java
  public void calculateScore() {}
  public boolean isGameOver() {}
  ```

- **Constantes**: Utilizar el estilo `UPPER_SNAKE_CASE`. Ejemplo:
  ```java
  public static final int MAX_PLAYERS = 4;
  public static final String GAME_TITLE = "Adventure Quest";
  ```

## Nombres de Archivos

Los nombres de los archivos deben seguir el estándar de Java, que generalmente coincide con el nombre de la clase principal dentro del archivo. Aquí hay algunos ejemplos:

```
GameEngine.java
PlayerStats.java
GameTitle.java
```

## Commits

Para los commits, seguimos la filosofía de [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/). Aquí hay algunos ejemplos:

```
feat(engine): add collision detection
fix(player): correct health decrement bug
chore(tests): add unit tests for new game feature
docs(readme): update setup instructions
```

## Branching

Nunca hagas push directamente a `main`. Siempre crea una nueva rama para implementar características o realizar cambios. Las ramas deben seguir el estilo `type/description`. Aquí hay algunos ejemplos:

```
feature/add-new-level
fix/correct-score-calculation
refactor/update-variable-names
chore/update-build-script
```

Una vez que hayas terminado de trabajar en tu rama, puedes fusionarla en `main`.
