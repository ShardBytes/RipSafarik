# Box2D errors

#### Expression: m_bodyCount > 0
Tried deleting a null body.

```kotlin
GameWorld.physics.destroyBody(body)
```

#### Expression: IsLocked() == false
Modifying physics world while it's being simulated.
// ???

```kotlin
entity.despawn() // ???
```