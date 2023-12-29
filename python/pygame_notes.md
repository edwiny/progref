# Pygame notes

## Rect

* Most functions in pygame accepts a Rect
* Properties:
   * top-left-x
   * top-left-y
   * width
   * height


## Surface 

Coordinates: (x,y) is (0,0) at top-left of surface.



## Blitting


Copies image data to another image or the screen.

```
screen.blit(image, (pos_x, pos_y))

# OR
screen.blit(image, Rect_taget_pos)

# OR

screen.blit(image, Rect_target_pos, Rect_source)



```

* the position argument
  * represents where the topleft corner of the source should be placed on the destination.
  * can also accept a Rect which will pick up pos from Rect.x and Rect.y
* Optional 3rd argument to specify a sub section in source image to copy


After blitting all images, call
```
pygame.display.update()
clock.tick(60)
```

## Image loading


```
player = pygame.image.load('player.bmp').convert()
background = pygame.image.load('liquid.bmp').convert()
```


## Clocks


Get time in milliseconds, since pygame.init() was called.
```
pygame.time.get_ticks()
```


Set a timer:
```
set_timer(event, millis)
```

`event` is instance of `pygame.event.Event`


Sleep a certain amount of time:

```
clock = pygame.time.Clock()
clock.tick(framerate)   # returns milliseconds since last call
# tick calls a sleep function under the hood

```


## Sprites

Every sprite class need to have these properties initialised:
* Sprite.image
* Sprite.rect




```
class Block(pygame.sprite.Sprite):

    # Constructor. Pass in the color of the block,
    # and its x and y position
    def __init__(self, color, width, height):
       # Call the parent class (Sprite) constructor
       pygame.sprite.Sprite.__init__(self)

       # Create an image of the block, and fill it with a color.
       # This could also be an image loaded from the disk.
       self.image = pygame.Surface([width, height])
       self.image.fill(color)

       # Fetch the rectangle object that has the dimensions of the image
       # Update the position of this object by setting the values of rect.x and rect.y
       self.rect = self.image.get_rect()
```

override `update()` method if you plan on group updating.



### Sprite Groups

* Group.update() - calls the `update()` method on all sprites contained in the group
* Group.draw() - blits the sprites to the given surfuce, using individual sprites' `image` and `rect` properties


