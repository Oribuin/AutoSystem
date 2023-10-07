> `This project discounted atm.`

# AutoSystem

AutoSystem automatically kills the creatures spawned by Spawners in the Minecraft game, calculates their rewards, and drops the rewards accordingly. Additionally, it automatically harvests crops when they are fully grown and deposits the harvested items on the ground. The piston requirement for the crop harvesting process can also be configured.

> Note: ` Auto Harvest module has not written atm. You can check my Farmer v6 Project to what is it like. `

## Configuration file
<details>
  <summary>config.yml</summary>

    # autoPiston feature available after v1.1
    autoPiston:
    # Feature of auto piston if you don't want
    # You can set it false
    feature: true
    # Checks requirements like require piston
    # on top for break etc.
    checks:
        # Plugin require PISTON TOP OF FARM for
        # auto break execute.
        # It may affect performance because
        # looking for piston.
        searchForPiston: true
        # Plugin require farmer for break it
        # if there is no farmer then it won't break it.
        requireFarmer: true
    allowedWorlds:
        - world
        - Skyblock
        - Survival
    allowedPlants:
        - REEDS
        - POTATOES
        - CARROTS

    # AutoKill feature kill mobs
    # which spawned on spawners
    autoKill:
    # Feature of autoKill if you don't want
    # You can set it false
    feature: true
    # Plugin require farmer for kill it
    # if there is no farmer then it won't kill.
    requireFarmer: true
    # Cook foods which will be dropped by
    # Spawner
    autoCook: true
    # Removing mob which is spawned recent
    # If you want to see death animation set it false
    # *death animation may cause performance issue*
    removeMob: true
    # Allowed worlds which killed automatically
    allowedWorlds:
        - world
        - Skyblock
        - Survival
    # You have to use one of them (Blacklist or Allowed)
    # If you set something to allowed plugin will kill only
    # allowed mobs. if you set something to blacklistedmobs
    # then plugin will kill all mobs except blacklisted mobs.
    # You can remove category one of them. (Blacklist or Allowed) !! USE 1 OF THEM !!
    # EntityType list: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html
    allowedMobs:
        - SHEEP
        - COW
        - CREEPER
    blacklistMobs:
        - VILLAGER
</details>

## Kullanılan Kütüphaneler

* [spigot-api (1.19-R0.2-SNAPSHOT)](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/spigot/browse)
* [lombok (LATEST)](https://github.com/projectlombok/lombok)
* [Glib](https://github.com/Geik-xyz/GLib)
* [WildStackerAPI](https://www.spigotmc.org/resources/⚡%EF%B8%8F-wildstacker-⚡%EF%B8%8F-spawners-entities-drops-blocks-⚡%EF%B8%8F-1-20-2-support.87404/)

## Contributing

We welcome contributions from the community! If you would like to contribute, please follow these guidelines:

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your feature or bug fix.
3. Make your changes, and ensure that your code is well-tested.
4. Create a pull request with a detailed description of your changes.

By contributing to this project, you agree to abide by the [Code of Conduct](CODE_OF_CONDUCT.md).