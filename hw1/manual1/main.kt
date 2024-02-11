class Generic<T : Any>(val cls: Class<T>) {
    companion object {
        inline operator fun <reified T : Any>invoke() = Generic(T::class.java)
    }

    fun checkType(t: Any) {
        when {
            cls.isAssignableFrom(t.javaClass) -> println("Correct type")
            else -> println("Wrong type")
       }
    }
}



typealias TypeID = Long

interface Animal {
    val id: TypeID
    val height: Double
}

interface LoudAnimal : Animal {
    fun sound(): String
}

class Cat(override val id: TypeID, override val height: Double): LoudAnimal {
    override fun sound(): String {
        return "purr"
    }
}

class Dog(override val id: TypeID, override val height: Double): LoudAnimal {
    override fun sound(): String {
        return "bow-wow"
    }
}

class Hippo(override val id: TypeID, override val height: Double): LoudAnimal {
    override fun sound(): String {
        return "hohoho"
    }
}

class Horse(override val id: TypeID, override val height: Double): LoudAnimal {
    override fun sound(): String {
        return "neigh"
    }
}

class Fish(override val id: TypeID, override val height: Double): Animal {
}



class Keeper(var id: TypeID, var name: String) {
}



class Zoo {
    var idToAnimal = HashMap<TypeID, Animal>()
    var animalIdToKeeper = HashMap<TypeID, Keeper>()

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
        this.animalIdToKeeper.put(animal.id, keeper)
    }

    fun findAnimalById(id: TypeID): Animal {
        return idToAnimal.getValue(id)
    }

    fun removeAnimalById(id: TypeID) {
        idToAnimal.remove(id)
        animalIdToKeeper.remove(id)
    }

    fun getAnimalsForKeeperId(id: TypeID): List<Animal> {
        TODO("Zoo::animalsForKeeperId")
    }

    fun getAnimalsForKeeperName(name: String): List<Animal> {
        TODO("Zoo::animalsForKeeperName")
    }

    fun getAnimalsHeigherThan(height: Double): List<Animal> {
        TODO("Zoo::getAnimalsHeigherThan")
    }

    fun getAllSoundAnimals(): List<Animal> {
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
    Zoo()
    var zooFull = Zoo(listOf(
        Cat(1, 0.25),
        Dog(2, 0.5),
        Hippo(3, 3.5),
        Horse(4, 1.8),
        Cat(5, 0.3)
    ))
    zooFull.addAnimal(Fish(6, 0.05))
    zooFull.findAnimalById(1)
    zooFull.findAnimalById(2)
    zooFull.removeAnimalById(2)
    try {
        zooFull.findAnimalById(2)
    } catch (e: java.util.NoSuchElementException) {
    }
    var keeperCats = Keeper(1, "keeper number one")
    var keeperBigGood = Keeper(2, "superhero underground")
    var keeperBigEvil = Keeper(3, "superhero underground")
    zooFull.attachKeeper(keeperCats, zooFull.findAnimalById(1))
    zooFull.attachKeeper(keeperCats, zooFull.findAnimalById(5))
    zooFull.attachKeeper(keeperBigGood, zooFull.findAnimalById(3))
    zooFull.attachKeeper(keeperBigEvil, zooFull.findAnimalById(4))
    // zooFull.getAnimalsForKeeperId(keeperCats.id)
    // zooFull.getAnimalsForKeeperName(keeperBigGood.name)
    // zooFull.getAnimalsHeigherThan(2.0)
    zooFull.getAllSoundAnimals()
    zooFull.getAllAnimalsOfType<Cat>().forEach { println(it.id) }
}

/*
@Test
fun `Adding 1 and 3 should be equal to 4`() {
    Assertions.assertEquals(4, calculator.add(1, 3))
}
*/

