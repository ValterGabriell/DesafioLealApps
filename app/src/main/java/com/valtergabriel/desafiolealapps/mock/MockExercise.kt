package com.valtergabriel.desafiolealapps.mock

import com.valtergabriel.desafiolealapps.R
import java.time.LocalDateTime

data class MockExercise(
    val name: String = "",
    val descriptiom: String = "",
    val icon: Int = 0
) {
    fun getExercicesForBiceps(): List<MockExercise> {
        val list = ArrayList<MockExercise>()
        list.add(
            MockExercise(
                "Flexão de braço",
                "Deitar de barriga para baixo, levantar o corpo esticando os braços alinhados ao corpo, em uma distância um pouco maior que a largura dos ombros, os pés no chão,  abdômen contraído e costas alinhadas. Levantar e abaixar o corpo apenas dobrando e esticando os braços em um ângulo de 90º com o cotovelo",
                R.drawable.bicepes
            )
        )
        list.add(
            MockExercise(
                "Flexão de braço inclinada",
                "pegar um uma superfície para criar a inclinação do seu corpo como Kettle, banquinho, cadeira, puff, bola de ginástica ou uma plataforma de exercício de step. Apoiar as mãos na superfície inclinada, com os braços alinhados ao corpo, em uma distância um pouco maior que a largura dos ombros e os pés no chão. O corpo deve reto com as costas alinhadas com o tronco. Contrair o abdômen, flexionar os cotovelos até que o peito encoste na superfície e retornar à posição inicial. Pode-se fazer 2 a 3 séries de 8 a 10 repetições cada, descansando de 60 a 90 segundos entre as séries.",
                R.drawable.bicepes
            )
        )
        return list
    }

    fun getExercicesForAbdomen(): List<MockExercise> {
        val list = ArrayList<MockExercise>()
        list.add(
            MockExercise(
                "Flexão do tronco (abdominal supra)",
                "Deitado no solo de barriga pra cima, com os pés apoiados no chão na linha do quadril e mantendo curvatura natural da lombar. Deve-se fazer uma flexão de tronco com contração do abdômen.",
                R.drawable.abdomen
            )
        )
        list.add(
            MockExercise(
                "Prancha ventral",
                "Apoie os cotovelos e a palma das mãos no chão, pernas estendidas com a ponta dos pés apoiadas no solo (mantenha o tronco e a cervical alinhados). Faça contração abdominal e leve o joelho em direção ao tronco.",
                R.drawable.abdomen
            )
        )
        return list
    }

    fun getExercicesForGetFit(): List<MockExercise> {
        val list = ArrayList<MockExercise>()
        list.add(
            MockExercise(
                "Agachamento com salto",
                "Realize um agachamento comum, com a flexão dos joelhos e do quadril, de maneira que os glúteos fiquem apontados para trás. No entanto, quando for subir, dê um salto para cima.",
                R.drawable.emagrecer
            )
        )
        list.add(
            MockExercise(
                "Corrida parada",
                "Você deverá ficar parado e simular os passos de uma corrida, elevando os joelhos o máximo que conseguir.",
                R.drawable.emagrecer
            )
        )
        return list
    }
}