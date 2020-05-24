package validate

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Table

@Table()
data class User (

        @GeneratedValue
        var id: Int = 0,

        @Column(name="name")
        var name: String = "",
        var email: String = "",
        var nickname: String = "",
        var password: String = ""
)
