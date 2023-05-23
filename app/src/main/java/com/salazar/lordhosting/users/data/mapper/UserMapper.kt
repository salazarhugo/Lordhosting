import com.salazar.lordhosting.users.data.response.UserResponse
import com.salazar.lordhosting.users.domain.models.User

fun UserResponse.toUser(): User {
    attributes.apply {
        return User(
            username = username,
            uuid = uuid,
            email = email,
            image = image,
            createdAt = created_at,
            twoFactorEnabled = twoFactorEnabled,
            permissionCount = permissions.size,
        )
    }
}