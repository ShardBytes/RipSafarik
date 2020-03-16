# Entity spawning:
#### Steps:
1. Create an entity object
1. Create entity body
1. (Optional) Set entity position
1. Add entity to GameMap

#### Code:
```kotlin
val newEntity = ZombieRunner()
newEntity.createBody()
newEntity.setPosition(5f, 5f)
GameMap.spawn(newEntity)
```
Or:
```kotlin
GameMap.spawn(ZombieRunner().apply { createBody(); setPosition(mapCoords) })
```

#### Expected behaviour:
New entity will be spawned into the world.

# Entity despawning:
#### Steps:
1. Despawn entity

#### Code:
```kotlin
entity.despawn()
```

#### Expected behaviour:
Entity will be despawned on the next world tick.
