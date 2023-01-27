package com.valtergabriel.desafiolealapps.dto

data class MockExercise(
    val name: Long = 0L,
    val title: String = "",
    val obs: String = "",
    val type: String = "",
    val duration: String = ""
) {
    fun bicepisExercises(): List<MockExercise> {
        val list = ArrayList<MockExercise>()
        list.add(
            MockExercise(
                0,
                "Flexão de braço",
                "Deitar de barriga para baixo, levantar o corpo esticando os braços alinhados ao corpo, em uma distância um pouco maior que a largura dos ombros, os pés no chão,  abdômen contraído e costas alinhadas. Levantar e abaixar o corpo apenas dobrando e esticando os braços em um ângulo de 90º com o cotovelo",
                "BICEPES",
                ""
            )
        )
        list.add(
            MockExercise(
                1,
                "Flexão de braço inclinada",
                "pegar um uma superfície para criar a inclinação do seu corpo como Kettle, banquinho, cadeira, puff, bola de ginástica ou uma plataforma de exercício de step. Apoiar as mãos na superfície inclinada, com os braços alinhados ao corpo, em uma distância um pouco maior que a largura dos ombros e os pés no chão. O corpo deve reto com as costas alinhadas com o tronco. Contrair o abdômen, flexionar os cotovelos até que o peito encoste na superfície e retornar à posição inicial. Pode-se fazer 2 a 3 séries de 8 a 10 repetições cada, descansando de 60 a 90 segundos entre as séries.",
                "BICEPES",
                ""
            )
        )
        return list
    }

    fun abdomenExercises(): List<MockExercise> {
        val list = ArrayList<MockExercise>()
        list.add(
            MockExercise(
                2,
                "Abdominal com perna elevada",
                "Nesse abdominal, a pessoa deve deitar no chão de barriga para cima, levantar as pernas esticadas ou semi flexionadas e o tronco, mantendo os braços para frente.\n" +
                        "\n" +
                        "Esse exercício é feito em isometria, ou seja, a pessoa deve permanecer na mesma posição por cerca de 15 a 30 segundos ou de acordo com indicação do instrutor, mantendo o abdômen contraído durante todo o tempo.",
                "ABDOMEN",
                ""
            )
        )
        list.add(
            MockExercise(
                3,
                "Abdominal em V",
                "Para fazer esse abdominal, a pessoa deve deitar com a barriga para cima e levantar um pouco as pernas e o tronco, colocando os braços para frente, realizando esses dois movimentos ao mesmo tempo.\n" +
                        "\n" +
                        "É recomendado realizar 3 séries de 8 repetições, ou de acordo com a indicação do instrutor, contraindo bem a musculatura abdominal e com cuidado para não forçar o pescoço.",
                "ABDOMEN",
                ""
            )
        )
        return list
    }


    fun getFit(): List<MockExercise> {
        val list = ArrayList<MockExercise>()
        list.add(
            MockExercise(
                4,
                "Agachamento com salto",
                "Realize um agachamento comum, com a flexão dos joelhos e do quadril, de maneira que os glúteos fiquem apontados para trás. No entanto, quando for subir, dê um salto para cima. “É um exercício poderoso para começarmos a sequência. Nele, temos alto gasto calórico, pois movimentamos o corpo como um todo (pernas, abdômen e braços para dar potência na hora da subida)",
                "EMAGRECER",
                ""
            )
        )
        list.add(
            MockExercise(
                5,
                "Corrida parada",
                "Você deverá ficar parado e simular os passos de uma corrida, elevando os joelhos o máximo que conseguir. “As corridas têm alto gasto calórico e, quando se empurra o calcanhar pro chão para levantar o joelho, se potencializa a ação localizada nos glúteos”, completa a especialista.",
                "EMAGRECER",
                ""
            )
        )
        return list
    }
}