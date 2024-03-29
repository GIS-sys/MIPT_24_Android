// Animal.kt

typealias TypeAnimalID = Long
typealias TypeAnimalHeight = Double

sealed interface Animal {
    val id: TypeAnimalID
    val height: TypeAnimalHeight
}

sealed interface LoudAnimal : Animal {
    fun sound(): String
}

class Cat(override val id: TypeAnimalID, override val height: TypeAnimalHeight): LoudAnimal {
    override fun sound(): String {
        return "purr"
    }
}

class Dog(override val id: TypeAnimalID, override val height: TypeAnimalHeight): LoudAnimal {
    override fun sound(): String {
        return "bow-wow"
    }
}

class Hippo(override val id: TypeAnimalID, override val height: TypeAnimalHeight): LoudAnimal {
    override fun sound(): String {
        return "hohoho"
    }
}

class Horse(override val id: TypeAnimalID, override val height: TypeAnimalHeight): LoudAnimal {
    override fun sound(): String {
        return "neigh"
    }
}

class Fish(override val id: TypeAnimalID, override val height: TypeAnimalHeight): Animal {
}

// Keeper.kt

typealias TypeKeeperID = Long

data class Keeper(var id: TypeKeeperID, var name: String)

// Zoo.kt

class Zoo {
    val keeperRoot = Keeper(0, "The Head of the Zoo")
    val idToAnimal = HashMap<TypeAnimalID, Animal>()
    val heightToAnimal = java.util.TreeMap<TypeAnimalHeight, HashMap<TypeAnimalID, Animal>>()
    val animalIdToKeeper = HashMap<TypeAnimalID, Keeper>()
    val keeperIdToAnimals = HashMap<TypeKeeperID, HashMap<TypeAnimalID, Animal>>()
    val keeperNameToAnimals = HashMap<String, HashMap<TypeAnimalID, Animal>>()

    // private methods

    private fun addKeeper(keeper: Keeper) {
        if (keeperIdToAnimals.contains(keeper.id)) {
            throw IllegalArgumentException("Zoo::addKeeper - keeper with this id already exists")
        }
        keeperIdToAnimals.put(keeper.id, HashMap<TypeAnimalID, Animal>())
        if (!keeperNameToAnimals.contains(keeper.name)) {
            keeperNameToAnimals.put(keeper.name, HashMap<TypeAnimalID, Animal>())
        }
    }

    private fun disconnectAnimalFromKeeper(animal: Animal) {
        val keeper: Keeper = animalIdToKeeper.getValue(animal.id)
        keeperIdToAnimals.getValue(keeper.id).remove(animal.id)
        keeperNameToAnimals.getValue(keeper.name).remove(animal.id)
        animalIdToKeeper.remove(animal.id)
    }

    private fun connectExistingKeeperAnimal(keeper: Keeper, animal: Animal) {
        if (animalIdToKeeper.contains(animal.id)) {
            disconnectAnimalFromKeeper(animal)
        }
        animalIdToKeeper.put(animal.id, keeper)
        keeperIdToAnimals.getValue(keeper.id).put(animal.id, animal)
        keeperNameToAnimals.getValue(keeper.name).put(animal.id, animal)
    }

    // public methods

    constructor() {
        addKeeper(keeperRoot)
    }

    constructor(animals: List<Animal>) {
        addKeeper(keeperRoot)
        animals.forEach {
            addAnimal(it)
        }
    }

    fun addAnimal(animal: Animal) {
        if (idToAnimal.contains(animal.id)) {
            throw IllegalArgumentException("Zoo::addAnimal - animal with this id already exists")
        }
        idToAnimal.put(animal.id, animal)
        if (!heightToAnimal.contains(animal.height)) {
            heightToAnimal.put(animal.height, HashMap<TypeAnimalID, Animal>())
        }
        heightToAnimal.getValue(animal.height).put(animal.id, animal)
        connectExistingKeeperAnimal(keeperRoot, animal)
    }

    fun attachKeeper(keeper: Keeper, animal: Animal) {
        if (!idToAnimal.contains(animal.id)) {
            addAnimal(animal)
        }
        if (!keeperIdToAnimals.contains(keeper.id)) {
            addKeeper(keeper)
        }
        connectExistingKeeperAnimal(keeper, animal)
    }

    fun removeAnimalById(id: TypeAnimalID) {
        if (!idToAnimal.contains(id)) {
            throw IllegalArgumentException("Zoo::removeAnimalById - no animal with this id")
        }
        val animalToDelete = idToAnimal.getValue(id)
        disconnectAnimalFromKeeper(animalToDelete)
        heightToAnimal.getValue(animalToDelete.height).remove(animalToDelete.id)
        idToAnimal.remove(animalToDelete.id)
    }

    fun findAnimalById(id: TypeAnimalID): Animal? {
        if (!idToAnimal.contains(id)) {
            return null
        }
        return idToAnimal.getValue(id)
    }

    fun getAnimalsForKeeperId(id: TypeKeeperID): Collection<Animal> {
        return keeperIdToAnimals.getValue(id).values
    }

    fun getAnimalsForKeeperName(name: String): Collection<Animal> {
        return keeperNameToAnimals.getValue(name).values
    }

    fun getAnimalsHeigherThan(height: TypeAnimalHeight): Collection<Animal> {
        return heightToAnimal.tailMap(height).values.map{it.values}.flatten()
    }

    // this could have been faster but i really dont want to add another hashmap "isLoud -> animal"
    fun getAllLoudAnimals(): List<Animal> {
        return idToAnimal.values.filter {
            when (it) {
                is LoudAnimal -> true
                else -> false
            }
        }
    }

    inline fun <reified T: Animal> getAllAnimalsOfType(): List<Animal> {
        return idToAnimal.values.filter { it is T }
    }
}

// Main.kt

fun main() {
    // Here are some tests for every point in task
    // To enable assertions run JVM with flag -ea
    // 1. default constructor
    Zoo()
    // 2. constructor from list of animals
    var zooFull = Zoo(listOf(
        Cat(1, 0.25),
        Dog(2, 0.5),
        Hippo(3, 3.5),
        Horse(4, 1.8),
        Cat(5, 0.3)
    ))
    assert(zooFull.idToAnimal.size == 5)
    // 3. add animal
    zooFull.addAnimal(Fish(6, 0.05))
    assert(zooFull.idToAnimal.size == 6)
    // 4. find and remove animal
    assert(zooFull.findAnimalById(1)?.height == 0.25)
    assert(zooFull.findAnimalById(2)?.height == 0.5)
    zooFull.removeAnimalById(2)
    assert(zooFull.findAnimalById(2) == null)
    // 5. attach keeper
    val keeperCats = Keeper(1, "keeper number one")
    val keeperBigGood = Keeper(2, "superhero underground")
    val keeperBigEvil = Keeper(3, "superhero underground")
    zooFull.attachKeeper(keeperCats, zooFull.findAnimalById(1)!!)
    zooFull.attachKeeper(keeperCats, zooFull.findAnimalById(5)!!)
    zooFull.attachKeeper(keeperBigGood, zooFull.findAnimalById(3)!!)
    zooFull.attachKeeper(keeperBigEvil, zooFull.findAnimalById(4)!!)
    assert(zooFull.animalIdToKeeper.size == zooFull.idToAnimal.size)
    assert(zooFull.keeperIdToAnimals.size == 3 + 1)
    assert(zooFull.keeperNameToAnimals.size == 2 + 1)
    // 6. animals for keeper id
    var catKeepersAnimals = zooFull.getAnimalsForKeeperId(keeperCats.id)
    assert(catKeepersAnimals.size == 2)
    catKeepersAnimals.forEach({ animal ->
        when (animal) {
            is Cat -> {}
            else -> throw AssertionError("catKeepersAnimals contained a non-cat animal")
        }
    })
    // 7. animals for keeper name
    // init
    var bigKeepersAnimals = zooFull.getAnimalsForKeeperName(keeperBigGood.name)
    assert(bigKeepersAnimals.size == 2)
    // delete one
    zooFull.removeAnimalById(3)
    var bigKeepersAnimalsAfterDeletingOne = zooFull.getAnimalsForKeeperName(keeperBigGood.name)
    assert(bigKeepersAnimalsAfterDeletingOne.size == 1)
    // delete two
    zooFull.removeAnimalById(4)
    var bigKeepersAnimalsAfterDeletingAll = zooFull.getAnimalsForKeeperName(keeperBigGood.name)
    assert(bigKeepersAnimalsAfterDeletingAll.size == 0)
    // add back two animals
    zooFull.addAnimal(Hippo(10, 3.6))
    zooFull.addAnimal(Horse(11, 2.1))
    // 8. animals with height bigger than
    val tallAnimals = zooFull.getAnimalsHeigherThan(1.5)
    assert(tallAnimals.size == 2)
    assert(tallAnimals.filter({ animal -> animal.height > 1.5 }).size == 2)
    // 9. animals that are able to make sounds
    val loudAnimals = zooFull.getAllLoudAnimals()
    assert(loudAnimals.size == 4)
    loudAnimals.forEach({ animal ->
        when (animal) {
            is LoudAnimal -> println(animal.sound())
            else -> throw AssertionError("loadAnimals contained an animal without a sound")
        }
    })
    // 10. get animals by type
    zooFull.getAllAnimalsOfType<Cat>().forEach {
        when (it) {
            is Cat -> {}
            else -> throw AssertionError("zooFull.getAllAnimalsOfType<Cat> contained a non-cat animal")
        }
    }
}


