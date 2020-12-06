# PARTE TEORICA

### Lifecycle

#### Explica el ciclo de vida de una Activity.

##### ¿Por qué vinculamos las tareas de red a los componentes UI de la aplicación?
Vinculamos las tareas de red (por ejemplo recuperar los streams más populares de Twitch a través de su API pública, como estamos realizando en esta PAC) con los componentes UI de la aplicación porque normalmente los datos que recuperamos de APIs externas (datos, imagenes...) queremos que sean mostrados al usuario a través de la interfaz gráfica de la aplicación. De esta manera, el contenido se carga dinámicamente y la experiencia de usuario es más personalizada.
Cabe considerar que estas operaciones, si se gestionan mediante corutinas de Kotlin, deberían ejecutarse a través del IO Dispatcher para la gestión de hilos, ya que está optimizado para estos casos de uso.

##### ¿Qué pasaría si intentamos actualizar la recyclerview con nuevos streams después de que el usuario haya cerrado la aplicación?
Si el usuario ha cerrado la aplicación, la activity que contiene el recycler view ya habrá sido destruida, los recursos reservados para el mismo habrán sido liberados y no se podrá actualizar.

##### Describe brevemente los principales estados del ciclo de vida de una Activity.
    - OnCreate(): Se ejecuta cuando se crea la activity por primera vez. Suele contener la lógica que se necesita para inicializar la aplicación. Ocurre sólo una vez.
    - OnResume(): Se ejecuta cuando la actividad vuelve al primer plano, y es en este momento cuando el usuario puede comenzar a interactuar con la aplicación.
    - onPause(): Se ejecuta cuando el usuario va a abandonar la actividad. Esto no siempre significa que la activdad vaya a ser destruida. Se Utiliza para pausar procedimientos que no deberían continuar mientras la activity esté en pausa pero que se pueden retomar cuando ésta se reanude.
    - onStop(): Se ejecuta cuando la activity ya no es visible para el usuario. Esto puede ocurrir, por ejemplo, cuando una nueva activity oculta la pantalla completa; o justo antes de finalizar la activity. Debe gestionar operativas como la persistencia de datos antes de terminar la aplicación a una base de datos.
    - onDestoy(): Se ejecuta antes de que se detruya la actividad. Aquí se deben liberar todos los recursos que no se hayan ya liberado en onStop().

---

### Paginación 

#### Explica el uso de paginación en la API de Twitch.

##### ¿Qué ventajas ofrece la paginación a la aplicación?
La paginación influye en la eficiencia de nuestra aplicación. Si no paginamos los resultados y obtenemos todos los elementos de una API (no sabemos si son 5 o 20000), el posible que la consulta sea muy compleja tanto para el servidor, como para nosotros a la hora de procesar los dato obtenidos, resultando en unos tiempos de espera demasiado largos para el usuario de nuestra aplicación.

##### ¿Qué problemas puede tener la aplicación si no se utiliza paginación?
Como se ha comentado en la pregunta anterior, si estamos consultando una fuente de datos con muchos elementos, esto puede causar que nuestra aplicación tenga tiempos de espera muy largos para nuestros usuarios y que su experiencia utilizando nuestra aplicación no sea positiva.

##### Lista algunos ejemplos de aplicaciones que usan paginación.
Algunas aplicaciones que realizan paginación son Instagram o twitter, que no cargan todos los posts al iniciarse, sino que recuperan los elementos poco a poco a medida que el usuario hace scroll.
