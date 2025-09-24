import kotlin.math.*

class Polynome() {
    var coeffs: DoubleArray = doubleArrayOf()
    var degree: Int = 0


    constructor(coeffs: DoubleArray) : this() {
        this.coeffs = coeffs.clone()
        degree = coeffs.size - 1
    }


    operator fun plus(other: Polynome): Polynome {
        var degree = max(this.degree, other.degree)
        var coeffs: DoubleArray = doubleArrayOf()
        for (i in 0..degree) {
            var x: Double = { t: Int -> if (t >= i) this.coeffs[i] else 0.0 }(this.degree)
            var y: Double = { t: Int -> if (t >= i) other.coeffs[i] else 0.0 }(other.degree)
            coeffs += x+y
        }
        return Polynome(coeffs)
    }

    override fun toString(): String {
        var str : String = ""
        for(i in 0..this.degree){
            if(i!=0) str += {t: Double -> if(t>0) "+" else ""}(coeffs[i])
            if(coeffs[i]==0.0) continue
            str += "${coeffs[i]}"
            if(this.degree - i != 0){
                str += "*x^${this.degree - i}"
            }
        }
        return str
    }
}

class Lagrange(points: DoubleArray) {
    private var points = points.clone()
    private var degree = points.size - 1

    constructor() : this(doubleArrayOf(0.0))

    fun function(x: Double): Double {
        return sin(x)
    }


}

fun main() {
    var a = Polynome(doubleArrayOf(1.0, 0.0, -3.0))
    var b = Polynome(doubleArrayOf(4.0, 5.0, 6.0, 7.0))
    var c = a + b
    println(c)
}
