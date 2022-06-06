from enum import Enum

class Colour(Enum):
    RED = "red"
    BLUE = "blue"

    def __str__(self):
        return self.value


    @classmethod
    def from_str(cls, text):
        for c in cls:
           if c.value == text:
              return c
        raise ValueError(f"'{text}' could not be converted to enum value")




for c in Colour:
   print(c)


e = Colour.from_str("red")

print(f"found enum: {e}")
#e1 = Colour.from_str("redd")
