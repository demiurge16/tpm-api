package net.nuclearprometheus.tpm.applicationserver.domain.model.client

import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country
import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.common.ValidationError
import net.nuclearprometheus.tpm.applicationserver.domain.validator.validate

class Client(
    id: ClientId = ClientId(),
    name: String,
    email: String,
    phone: String,
    address: String,
    city: String,
    state: String,
    zip: String,
    country: Country,
    vat: String? = null,
    notes: String,
    type: ClientType,
    active: Boolean = true
): Entity<ClientId>(id) {

    init {
        validate {
            assert { name.isNotBlank() } otherwise {
                ValidationError("name", "Name cannot be blank")
            }
            assert { email.isNotBlank() } otherwise {
                ValidationError("email", "Email cannot be blank")
            }
            assert { phone.isNotBlank() } otherwise {
                ValidationError("phone", "Phone cannot be blank")
            }
            assert { address.isNotBlank() } otherwise {
                ValidationError("address", "Address cannot be blank")
            }
            assert { city.isNotBlank() } otherwise {
                ValidationError("city", "City cannot be blank")
            }
            assert { state.isNotBlank() } otherwise {
                ValidationError("state", "State cannot be blank")
            }
            assert { zip.isNotBlank() } otherwise {
                ValidationError("zip", "Zip cannot be blank")
            }
            assert { type.corporate && !vat.isNullOrBlank() } otherwise {
                ValidationError("vat", "VAT cannot be blank for corporate clients")
            }
            assert { type.active } otherwise {
                ValidationError("type", "Client type cannot be inactive")
            }
        }
    }

    var name: String = name; private set
    var email: String = email; private set
    var phone: String = phone; private set
    var address: String = address; private set
    var city: String = city; private set
    var state: String = state; private set
    var zip: String = zip; private set
    var country: Country = country; private set
    var vat: String? = vat; private set
    var notes: String = notes; private set
    var type: ClientType = type; private set
    var active: Boolean = active; private set

    fun update(
        name: String,
        email: String,
        phone: String,
        address: String,
        city: String,
        state: String,
        zip: String,
        country: Country,
        vat: String?,
        notes: String,
        type: ClientType
    ) {
        val typeChanged = { this.type.id != type.id }

        validate {
            assert { name.isNotBlank() } otherwise {
                ValidationError("name", "Name cannot be blank")
            }
            assert { email.isNotBlank() } otherwise {
                ValidationError("email", "Email cannot be blank")
            }
            assert { phone.isNotBlank() } otherwise {
                ValidationError("phone", "Phone cannot be blank")
            }
            assert { address.isNotBlank() } otherwise {
                ValidationError("address", "Address cannot be blank")
            }
            assert { city.isNotBlank() } otherwise {
                ValidationError("city", "City cannot be blank")
            }
            assert { state.isNotBlank() } otherwise {
                ValidationError("state", "State cannot be blank")
            }
            assert { zip.isNotBlank() } otherwise {
                ValidationError("zip", "Zip cannot be blank")
            }
            assertIf(typeChanged) { type.corporate && !vat.isNullOrBlank() } otherwise {
                ValidationError("vat", "VAT cannot be blank for corporate clients")
            }
            assertIf(typeChanged) { type.active } otherwise {
                ValidationError("type", "Client type cannot be inactive")
            }
        }

        this.name = name
        this.email = email
        this.phone = phone
        this.address = address
        this.city = city
        this.state = state
        this.zip = zip
        this.country = country
        this.vat = vat
        this.notes = notes
        this.type = type
    }

    fun activate() {
        active = true
    }

    fun deactivate() {
        active = false
    }
}