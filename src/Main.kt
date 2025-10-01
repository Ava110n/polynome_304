import com.sun.source.util.Plugin
import kotlin.math.*

open class Polynome {
    var coeffs: DoubleArray
    var degree: Int

    constructor(coeffs: DoubleArray) {
        this.coeffs = clean(coeffs)
        this.degree = this.coeffs.size - 1
    }

    constructor() : this(doubleArrayOf(0.0))
    constructor(i: Int) : this(DoubleArray(i, { 0.0 }))


    fun clean(coeffs: DoubleArray):DoubleArray{
        var my_coeffs = coeffs
        var count = 0
        for(i in my_coeffs.size - 1 downTo 0)
            if(my_coeffs[i] == 0.0)
                count++
            else
                break
        my_coeffs = my_coeffs.dropLast(count).toDoubleArray()
        return my_coeffs
    }

    override fun toString(): String {
        var str = ""
        for (i in this.degree downTo 0) {
            if (coeffs[i] == 0.0) continue
            str += { c: Double -> if (c > 0) " + " else " - " }(coeffs[i])
            if (str == " + ") str = ""
            str += "${abs(coeffs[i])}"
            if (i != 0) str += "*x^$i"
        }
        return str
    }

    operator fun plus(other: Polynome): Polynome {
        var my_degree = max(this.degree, other.degree)
        var my_coeffs: DoubleArray = DoubleArray(my_degree + 1, { 0.0 })
        for (i in 0..my_degree) {
            val c = { c: DoubleArray, d: Int -> if (d >= i) c[i] else 0.0 }
            val x = c(this.coeffs, this.degree)
            val y = c(other.coeffs, other.degree)
            my_coeffs[i] += x + y
        }
        return Polynome(my_coeffs)
    }

    operator fun times(k: Double):Polynome{
        return Polynome(DoubleArray(this.degree+1, {i:Int->coeffs[i]*k}))
    }

    operator fun times(k: Int) = this.times(k*1.0)
    //operator fun Int.times(p: Polynome) = p.times(this)

    operator fun times(other:Polynome):Polynome{
        var my_coeffs = DoubleArray(this.degree+other.degree+1, {0.0})
        for(i in 0..this.degree)
            for( j in 0..other.degree)
                my_coeffs[i+j] += this.coeffs[i]*other.coeffs[j]
        return Polynome(my_coeffs)
    }
}

operator fun Double.times(p: Polynome) = p.times(this)
operator fun Int.times(p:Polynome) = p.times(this)

class Lagrange:Polynome{
    var points: DoubleArray

    constructor(points: DoubleArray):super(){
        this.degree = points.size - 1
        this.points = points
        this.coeffs = calculate().coeffs
    }

    fun calculate():Polynome{
        var p = Polynome(doubleArrayOf(0.0))
        for(i in 0.. points.size-1) {
            var x = basis_polynome(i)
            p += function(points[i]) * basis_polynome(i)
        }
        return p
    }

    fun basis_polynome(i: Int):Polynome{
        var p: Polynome = Polynome(doubleArrayOf(1.0))
        for(j in 0..points.size-1)
            if(j!=i)
                p *= Polynome(doubleArrayOf(-points[j]/(points[i]-points[j]), 1/(points[i]-points[j])))
        return p
    }
    fun function(x:Double) = sin(x)

}


fun main() {
    var l = Lagrange(doubleArrayOf(0.0,1.0,2.0))
    println(l)
}
