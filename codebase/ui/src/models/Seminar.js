export class Seminar {
    /**
     * @param bookable true or false
     * @param agenda the agenda of the seminar
     * @param category the category of the seminar
     * @param contactPerson the contact person of the seminar
     * @param costsPerParticipant the costs per participant in euro
     * @param cycle how often the seminar will be
     * @param dates the dates of the seminar
     * @param description the description of the seminar
     * @param duration the duration of the seminar
     * @param goal the goal of the seminar
     * @param maximumParticipants the maximum number of participants
     * @param name the name of the seminar
     * @param requirements the requirements for the seminar
     * @param targetLevel the target level of the seminar from 1-5
     * @param trainer the trainer of the seminar
     * @param trainingType the training type of the seminar
     * @param bookingTimelog the booking timelog
     */
    constructor(name, description, agenda, bookable, category, targetLevel, requirements, trainer, contactPerson, trainingType, maximumParticipants, costsPerParticipant, bookingTimelog, goal, duration, cycle, dates) {
        this.name = name;
        this.description = description;
        this.agenda = agenda;
        this.bookable = bookable;
        this.category = category;
        this.targetLevel = targetLevel;
        this.requirements = requirements;
        this.trainer = trainer;
        this.contactPerson = contactPerson;
        this.trainingType = trainingType;
        this.maximumParticipants = maximumParticipants;
        this.costsPerParticipant = costsPerParticipant;
        this.bookingTimelog = bookingTimelog;
        this.goal = goal;
        this.duration = duration;
        this.cycle = cycle;
        this.dates = dates;
    }
}
export {Seminar as default}