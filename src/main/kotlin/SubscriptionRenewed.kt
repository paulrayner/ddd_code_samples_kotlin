package warranty

import java.time.LocalDate
import java.util.*

class SubscriptionRenewed(val contractId: UUID, val reason: String) : ContractEvent() {
    val occurredOn: LocalDate = LocalDate.now()
}