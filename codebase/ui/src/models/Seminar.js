export class Seminar {
    seminarName = '';
    dates = '';
    agenda = '';
    trainer = '';
    trainingType = '';
    contactPerson = '';
    costsPerParticipant = 0;
    description = 0;
    maximumParticipants = 0;
    planedAdvancement = '';
    regularCycle = '';
    reporting = '';
    requirements = '';
    targetAudiance = '';
    creationDate = '';

    constructor(seminarName, dates, agenda, trainer, trainingType, contactPerson, costsPerParticipant, description, maximumParticipants, planedAdvancement, regularCycle, reporting, requirements, targetAudiance) {
        this.seminarName = seminarName;
        this.dates = dates;
        this.agenda = agenda;
        this.trainer = trainer;
        this.trainingType = trainingType;
        this.contactPerson = contactPerson;
        this.costsPerParticipant = costsPerParticipant;
        this.description = description;
        this.maximumParticipants = maximumParticipants;
        this.planedAdvancement = planedAdvancement;
        this.regularCycle = regularCycle;
        this.reporting = reporting;
        this.requirements = requirements;
        this.targetAudiance = targetAudiance;
        this.creationDate = Date.prototype.toDateString();
    }
}