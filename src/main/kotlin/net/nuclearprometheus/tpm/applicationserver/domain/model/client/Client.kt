package net.nuclearprometheus.tpm.applicationserver.domain.model.client

import net.nuclearprometheus.tpm.applicationserver.domain.exceptions.client.ClientValidationException
import net.nuclearprometheus.tpm.applicationserver.domain.model.common.Entity
import net.nuclearprometheus.tpm.applicationserver.domain.model.dictionaries.Country

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
    vat: String,
    notes: String,
    type: ClientType,
    active: Boolean = true
): Entity<ClientId>(id) {

    var name: String = name; private set
    var email: String = email; private set
    var phone: String = phone; private set
    var address: String = address; private set
    var city: String = city; private set
    var state: String = state; private set
    var zip: String = zip; private set
    var country: Country = country; private set
    var vat: String = vat; private set
    var notes: String = notes; private set
    var type: ClientType = type; private set
    var active: Boolean = active; private set

    private fun validateName() {
        if (name.isBlank()) {
            throw ClientValidationException("Client name cannot be blank")
        }
    }

    private fun validateEmail() {
        if (email.isBlank()) {
            throw ClientValidationException("Client email cannot be blank")
        }
        if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$"))) {
            throw ClientValidationException("Client email is not valid")
        }
    }

    fun update(
        name : String,
        email : String,
        phone : String,
        address : String,
        city : String,
        state : String,
        zip : String,
        country : Country,
        vat : String,
        notes : String,
        type : ClientType
    ) {
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

        validateName()
        validateEmail()
    }

    fun activate() {
        active = true
    }

    fun deactivate() {
        active = false
    }
}