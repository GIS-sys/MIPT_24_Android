typealias TypeID = Long
typealias TypeHeight = Double

interface Animal {
    val id: TypeID
    val height: TypeHeight
}

interface LoudAnimal : Animal {
    fun sound(): String
}

class Cat(override val id: TypeID, override val height: TypeHeight): LoudAnimal {
    override fun sound(): String {
        return "purr"
    }
}

class Dog(override val id: TypeID, override val height: TypeHeight): LoudAnimal {
    override fun sound(): String {
        return "bow-wow"
    }
}

class Hippo(override val id: TypeID, override val height: TypeHeight): LoudAnimal {
    override fun sound(): String {
        return "hohoho"
    }
}

class Horse(override val id: TypeID, override val height: TypeHeight): LoudAnimal {
    override fun sound(): String {
        return "neigh"
    }
}

class Fish(override val id: TypeID, override val height: TypeHeight): Animal {
}



class Keeper(var id: TypeID, var name: String) {
}



class Zoo {
    var idToAnimal = HashMap<TypeID, Animal>()
    var animalIdToKeeper = HashMap<TypeID, Keeper>()
    var keeperIdToAnimals = HashMap<TypeID, HashMap<TypeID, Animal>>()
    var keeperNameToAnimals = HashMap<String, HashMap<TypeID, Animal>>()
    var keeperNameToAnimalsAmount = HashMap<String, HashMap<TypeID, Long>>()

    constructor() {
    }

    constructor(animals: List<Animal>) {
        animals.forEach {
            this.addAnimal(it)
        }
    }

    fun addAnimal(animal: Animal) {
        this.idToAnimal.put(animal.id, animal)
    }

    fun attachKeeper(keeper: Keeper, animal: Animal) {
        if (!this.keeperIdToAnimals.contains(keeper.id)) {
            this.keeperIdToAnimals.put(keeper.id, HashMap<TypeID, Animal>())
        }
        if (!this.keeperNameToAnimals.contains(keeper.name)) {
            this.keeperNameToAnimals.put(keeper.name, HashMap<TypeID, Animal>())
            this.keeperNameToAnimalsAmount.put(keeper.name, HashMap<TypeID, Long>())
        }
        if (!this.keeperNameToAnimalsAmount.getValue(keeper.name).contains(animal.id)) {
            this.keeperNameToAnimalsAmount.getValue(keeper.name).put(animal.id, 0L)
        }
        // if already exists keeper for this animal
        if (this.animalIdToKeeper.contains(animal.id)) {
            throw IllegalArgumentException("Zoo::attachKeeper - animal already has a keeper")
        }
        this.keeperIdToAnimals.getValue(keeper.id).put(animal.id, animal)
        this.keeperNameToAnimals.getValue(keeper.name).put(animal.id, animal)
        val prevAmount = this.keeperNameToAnimalsAmount.getValue(keeper.name).getValue(animal.id)
        this.keeperNameToAnimalsAmount.getValue(keeper.name).put(animal.id, prevAmount + 1)
        this.animalIdToKeeper.put(animal.id, keeper)
    }

    fun removeAnimalById(id: TypeID) {
        idToAnimal.remove(id)
        if (animalIdToKeeper.contains(id)) {
            val keeper = animalIdToKeeper.getValue(id)
            this.keeperIdToAnimals.getValue(keeper.id).remove(id)
            val prevAmount = this.keeperNameToAnimalsAmount.getValue(keeper.name).getValue(id)
            if (prevAmount == 1L) {
                this.keeperNameToAnimals.getValue(keeper.name).remove(id)
            }
            this.keeperNameToAnimalsAmount.getValue(keeper.name).put(id, prevAmount - 1)
            animalIdToKeeper.remove(id)
        }
    }

    fun findAnimalById(id: TypeID): Animal {
        return idToAnimal.getValue(id)
    }

    fun getAnimalsForKeeperId(id: TypeID): List<Animal> {
        return ArrayList(keeperIdToAnimals.getValue(id).values)
    }

    fun getAnimalsForKeeperName(name: String): List<Animal> {
        return ArrayList(keeperNameToAnimals.getValue(name).values)
    }

    // since as far as task implies height may be random,
    //   such structures as TreeMap will not be faster
    fun getAnimalsHeigherThan(height: TypeHeight): List<Animal> {
        return this.idToAnimal.values.filter { it.height > height }
    }

    fun getAllLoudAnimals(): List<Animal> {
        return this.idToAnimal.values.filter {
            when (it) {
                is LoudAnimal -> true
                else -> false
            }
        }
    }

    inline fun <reified T: Animal> getAllAnimalsOfType(): List<Animal> {
        return this.idToAnimal.values.filter { T::class.java.isAssignableFrom(it.javaClass) }
    }
}



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
    assert(zooFull.findAnimalById(1).height == 0.25)
    assert(zooFull.findAnimalById(2).height == 0.5)
    zooFull.removeAnimalById(2)
    try {
        zooFull.findAnimalById(2)
    } catch (e: java.util.NoSuchElementException) {
    }
    // 5. attach keeper
    val keeperCats = Keeper(1, "keeper number one")
    val keeperBigGood = Keeper(2, "superhero underground")
    val keeperBigEvil = Keeper(3, "superhero underground")
    zooFull.attachKeeper(keeperCats, zooFull.findAnimalById(1))
    zooFull.attachKeeper(keeperCats, zooFull.findAnimalById(5))
    zooFull.attachKeeper(keeperBigGood, zooFull.findAnimalById(3))
    zooFull.attachKeeper(keeperBigEvil, zooFull.findAnimalById(4))
    assert(zooFull.animalIdToKeeper.size == 4)
    assert(zooFull.keeperIdToAnimals.size == 3)
    assert(zooFull.keeperNameToAnimals.size == 2)
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
    // 10.
    zooFull.getAllAnimalsOfType<Cat>().forEach {
        when (it) {
            is Cat -> {}
            else -> throw AssertionError("zooFull.getAllAnimalsOfType<Cat> contained a non-cat animal")
        }
    }
}


