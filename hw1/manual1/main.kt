interface Animal {
    val id: Long
    val height: Double
}

interface LoudAnimal : Animal {
    fun sound(): String
}

class Cat(override val id: Long, override val height: Double): LoudAnimal {
    override fun sound(): String {
        return "purr"
    }
}

class Dog(override val id: Long, override val height: Double): LoudAnimal {
    override fun sound(): String {
        return "bow-wow"
    }
}

class Hippo(override val id: Long, override val height: Double): LoudAnimal {
    override fun sound(): String {
        return "hohoho"
    }
}

class Horse(override val id: Long, override val height: Double): LoudAnimal {
    override fun sound(): String {
        return "neigh"
    }
}

class Fish(override val id: Long, override val height: Double): Animal {
}



class Keeper(var id: Long, var name: String) {
}



class Zoo {
    constructor() {
        TODO("Zoo()") //println(it)
    }

    constructor(animals: List<Animal>?) {
        TODO("Zoo(animals)") //animals?.forEach { println(it) }
    }

    fun addAnimal(animal: Animal) {
        TODO("Zoo::addAnimal")
    }

    fun attachKeeper(keeper: Keeper, animal: Animal) {
        TODO("Zoo::attachKeeper")
    }

    fun findAnimalById(id: Long): Animal {
        TODO("Zoo::findAnimalById")
    }

    fun removeAnimalById(id: Long) {
        TODO("Zoo::findAnimalById")
    }

    fun getAnimalsForKeeperId(id: Long): List<Animal> {
        TODO("Zoo::animalsForKeeperId")
    }

    fun getAnimalsForKeeperName(name: String): List<Animal> {
        TODO("Zoo::animalsForKeeperName")
    }

    fun getAnimalsHeigherThan(height: Double): List<Animal> {
        TODO("Zoo::getAnimalsHeigherThan")
    }

    fun getAllSoundAnimals(): List<Animal> {
        TODO("Zoo::getAllSoundAnimals")
    }

    fun <T:Animal> getAllAnimalsOfType(): List<T> {
        TODO("Zoo::getAllAnimalsOfType")
    }
}



fun main(args: Array<String>) {
    var zooEmpty = Zoo()
    var zooFull = Zoo(listOf(
        Cat(1, 0.25),
        Dog(2, 0.5),
        Hippo(3, 3.5),
        Horse(4, 1.8),
        Cat(5, 0.3)
    ))
    zooFull.addAnimal(Fish(6, 0.05))
    zooFull.findAnimalById(2)
    zooFull.findAnimalById(5)
    zooFull.removeAnimalById(5)
    zooFull.findAnimalById(5)
    var keeperCats = Keeper(1, "keeper number one")
    var keeperBigGood = Keeper(2, "superhero underground")
    var keeperBigEvil = Keeper(3, "superhero underground")
    zooFull.attachKeeper(keeperCats, zooFull.findAnimalById(1))
    zooFull.attachKeeper(keeperCats, zooFull.findAnimalById(5))
    zooFull.attachKeeper(keeperBigGood, zooFull.findAnimalById(3))
    zooFull.attachKeeper(keeperBigEvil, zooFull.findAnimalById(4))
    zooFull.getAnimalsForKeeperId(keeperCats.id)
    zooFull.getAnimalsForKeeperName(keeperBigGood.name)
    zooFull.getAnimalsHeigherThan(2.0)
    zooFull.getAllSoundAnimals()
    zooFull.getAllAnimalsOfType<Cat>()
    
}

/*
@Test
fun `Adding 1 and 3 should be equal to 4`() {
    Assertions.assertEquals(4, calculator.add(1, 3))
}
*/

