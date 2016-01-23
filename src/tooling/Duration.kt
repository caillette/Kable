package tooling


class Duration( val milliseconds : Long ) {

  constructor( value : Long, unit : Unit ) : this( value * unit.multiplierToMilliseconds)

}

enum class Unit( val multiplierToMilliseconds : Long ) {
  HOUR( 3600 * 1000 ), MINUTE( 60 * 1000 ), SECOND( 1000 ), MILLISECOND( 1 )
}

