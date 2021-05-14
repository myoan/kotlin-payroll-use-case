import org.kodein.di.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

interface Calc {
    fun calc(d1: Int, d2: Int): Int
    fun calcDef(d: Int): Int
}

class AddInjection(private val default: Int) : Calc {
    override fun calc(d1: Int, d2: Int): Int {
        return d1 + d2
    }

    override fun calcDef(d: Int): Int {
        return default + d
    }
}

val kodeinInjection = DI {
    bind<Calc>() with provider { AddInjection(instance()) }
    bind<Int> { provider { 33 } }
}

/*
data class HelloMessageData(val message: String = "Hello")

interface HelloService {
    fun hello(): String
}

class HelloServiceImpl(private val helloMessageData: HelloMessageData): HelloService {
    override fun hello() = "Hey, ${helloMessageData.message}"
}

class HelloApplication: KoinComponent {
    val helloService by inject<HelloService>()
    fun sayHello() = println(helloService.hello())
}

val helloModule = module {
    single { HelloMessageData() }
    single { HelloServiceImpl(get()) as HelloService }
}


 */