# TubesPlus
Original: https://github.com/enhancedportals/TubeTransportSystem
Credits go to [polyrobot](https://www.curseforge.com/members/polyrobot/projects)
for the original mods Tube Transport System.

# Documentation

WORK IN PROGRESS!

## Tubes
Transport tubes can transport entities - this includes dropped items, arrows, boats, mobs, players, etc. These tubes are linked in a *Tube Network*. 

### Rotating

You can either face the direction you want the tube to face when you place it, 
or you can use [a wrench mod like this.](https://www.curseforge.com/minecraft/mc-mods/wrench)

## Tube Network
A tube network is made up of all tubes that are directly connected to each other. However, diagonal tubes do not connect. Each tube network can be configured to change the speed of transportation (shift-right clicking with an empty hand on any of the tubes), and can also be configured to route entities to a certain *destination tube*. See more about tube routing on the next section below.

## Routing
There are two tiers of *Tube Routers*: basic, and advanced.

The **basic** router is a block that will route any entities that go through it to a destination tube
that is selected in the router GUI and conencted to the network. 

The **advanced** tube router is more customizable then the basic router.
It allows you to specify a time to keep routing entities before switching to the *default* destination. 
The advanced router can also be configured to change to another destination after the timer has ended
instead of using the default.
