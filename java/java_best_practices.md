# Best Practices


## Null return values

Using null to represent the absence of a value is wrong.

Do not return `null` values from your methods, because you will always need to add boiler plate code when using that method to check for the special null case.

E.g.

```
String version = computer.getSoundcard().getUSB().getVersion();
```
would have to become:

```
String version = "UNKNOWN";
if(computer != null){
  Soundcard soundcard = computer.getSoundcard();
  if(soundcard != null){
    USB usb = soundcard.getUSB();
    if(usb != null){
      version = usb.getVersion();
    }
  }
}
```

In stead, use the `Optional` class.