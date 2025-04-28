# Challenge Conversor de Monedas - ONE

En este repositorio se encuentra mi solución propuesta al **challenge de Alura**.

Para cumplir con este ejercicio utilicé la **API ExchangeRate**, que permite realizar la conversión de divisas de manera actualizada.  
La interacción con el usuario se realiza a través de la **consola**, utilizando la clase **Scanner**.  
La **deserialización** de los datos recibidos en formato JSON la implementé mediante la librería **Gson**.

## Estructura del Proyecto

El desarrollo está organizado en **cuatro clases principales**:

- **Principal**: contiene el método `main`, donde se ejecuta el programa.
- **MonedasDisponibles**: clase `enum` que define las monedas permitidas en el conversor. Se Utiliza `valueOf` en la clase `Principal` para verificar si la moneda ingresada por el usuario es válida.
- **DatosConversorDeMonedas**: clase `record` que modela la respuesta de la API y facilita la deserialización del JSON.
- **ConversorDeMonedas**: contiene la lógica de conversión entre monedas, utilizando los datos recibidos del record.

## Funcionamiento

El programa solicita al usuario:

1. **Moneda base** (desde la cual se realizará la conversión).
2. **Valor** que desea convertir.
3. **Moneda destino** (a la cual desea convertir el valor).

Una vez ingresados los datos, se realiza la conversión y se muestra:

- El resultado de la conversión.
- La fecha de actualización de los datos, de acuerdo a la API.

**Nota**: El uso de una clase `enum` para definir monedas específicas fue una decisión para aplicar conceptos de programación y lógica, aunque técnicamente, sin esta restricción, se podría usar cualquier moneda disponible en la API.

## Tecnologías utilizadas

- Java
- API ExchangeRate
- Gson
- Programación orientada a objetos (POO)

## Agradecimientos

Gracias por visitar este proyecto. ¡Espero que te sea de utilidad!
