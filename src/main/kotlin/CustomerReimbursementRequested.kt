package warranty

import java.time.LocalDate
import java.util.UUID

class CustomerReimbursementRequested(val contractId: UUID, val repName: String, val reason: String) : ContractEvent() {
    val occurredOn: LocalDate = LocalDate.now()
}