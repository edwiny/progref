from enum import Enum

class ShardPurpose(Enum):
    STANDARD = "standard"
    SECURITY = "security"
    DOGFOOD = "dogfood"
    PERFORMANCE = "performance"
    QUARANTINE = "quarantine"
    DEDICATED = "dedicated"

    def __str__(self):
        return self.value




val = ShardPurpose.STANDARD


print("the value is {val}".format(val=val))



