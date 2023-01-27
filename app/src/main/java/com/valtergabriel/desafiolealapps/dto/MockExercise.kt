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
                "0"
            )
        )
        list.add(
            MockExercise(
                1,
                "Flexão de braço inclinada",
                "pegar um uma superfície para criar a inclinação do seu corpo como Kettle, banquinho, cadeira, puff, bola de ginástica ou uma plataforma de exercício de step. Apoiar as mãos na superfície inclinada, com os braços alinhados ao corpo, em uma distância um pouco maior que a largura dos ombros e os pés no chão. O corpo deve reto com as costas alinhadas com o tronco. Contrair o abdômen, flexionar os cotovelos até que o peito encoste na superfície e retornar à posição inicial. Pode-se fazer 2 a 3 séries de 8 a 10 repetições cada, descansando de 60 a 90 segundos entre as séries.",
                "BICEPES",
                "0"
            )
        )
        return list
    }

    fun abdomenExercises(): List<MockExercise> {
        val list = ArrayList<MockExercise>()
        list.add(
            MockExercise(
                2,
                "Flexão de braço",
                "Deitar de barriga para baixo, levantar o corpo esticando os braços alinhados ao corpo, em uma distância um pouco maior que a largura dos ombros, os pés no chão,  abdômen contraído e costas alinhadas. Levantar e abaixar o corpo apenas dobrando e esticando os braços em um ângulo de 90º com o cotovelo",
                "BICEPES",
                "0"
            )
        )
        list.add(
            MockExercise(
                3,
                "Flexão de braço inclinada",
                "pegar um uma superfície para criar a inclinação do seu corpo como Kettle, banquinho, cadeira, puff, bola de ginástica ou uma plataforma de exercício de step. Apoiar as mãos na superfície inclinada, com os braços alinhados ao corpo, em uma distância um pouco maior que a largura dos ombros e os pés no chão. O corpo deve reto com as costas alinhadas com o tronco. Contrair o abdômen, flexionar os cotovelos até que o peito encoste na superfície e retornar à posição inicial. Pode-se fazer 2 a 3 séries de 8 a 10 repetições cada, descansando de 60 a 90 segundos entre as séries.",
                "BICEPES",
                "0"
            )
        )
        return list
    }


    fun getFit(): List<MockExercise> {
        val list = ArrayList<MockExercise>()
        list.add(
            MockExercise(
                4,
                "Flexão de braço",
                "Deitar de barriga para baixo, levantar o corpo esticando os braços alinhados ao corpo, em uma distância um pouco maior que a largura dos ombros, os pés no chão,  abdômen contraído e costas alinhadas. Levantar e abaixar o corpo apenas dobrando e esticando os braços em um ângulo de 90º com o cotovelo",
                "BICEPES",
                "0"
            )
        )
        list.add(
            MockExercise(
                5,
                "Flexão de braço inclinada",
                "pegar um uma superfície para criar a inclinação do seu corpo como Kettle, banquinho, cadeira, puff, bola de ginástica ou uma plataforma de exercício de step. Apoiar as mãos na superfície inclinada, com os braços alinhados ao corpo, em uma distância um pouco maior que a largura dos ombros e os pés no chão. O corpo deve reto com as costas alinhadas com o tronco. Contrair o abdômen, flexionar os cotovelos até que o peito encoste na superfície e retornar à posição inicial. Pode-se fazer 2 a 3 séries de 8 a 10 repetições cada, descansando de 60 a 90 segundos entre as séries.",
                "BICEPES",
                "0"
            )
        )
        return list
    }
}