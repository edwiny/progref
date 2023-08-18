# Dataclasses

Note: normally variables defined outside of `__init__` are class variables, but this works differently for dataclasses
(or tbd, could be because they are type annotated.)

```
from dataclasses import dataclass

@dataclass
class Position:
    name: str
    lon: float
    lat: float
```

## Default values

Works the same way if you 

```
@dataclass
class Position:
    name: str
    lon: float = 0.0
    lat: float = 0.0
 ```

## Immutable data classes

```
@dataclass(frozen=True)
class Position:
    name: str
    lon: float = 0.0
    lat: float = 0.0
```

