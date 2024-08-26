package lab1

class Major (val name: String) {
    private val students = ArrayList<Student>()

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun stats(): Triple<Double, Double, Double> {

        var majorMin = Double.MAX_VALUE
        var majorMax = Double.MIN_VALUE
        var totalAverage = 0.0

        for (student in students) {
            val studentAverage = student.weightedAverage()
            majorMin = minOf(majorMin, studentAverage)
            majorMax = maxOf(majorMax, studentAverage)
            totalAverage += studentAverage
        }

        val majorAverage = totalAverage / students.size

        return Triple(majorMin, majorMax, majorAverage)
    }

    fun stats(courseName: String): Triple<Double, Double, Double> {
        var totalGrades = 0.0
        var totalStudents = 0
        var minGrade = Double.MAX_VALUE
        var maxGrade = Double.MIN_VALUE

        for (student in students) {
            for (course in student.courses) {
                if (course.name == courseName) {
                    minGrade = minOf(minGrade, course.grade)
                    maxGrade = maxOf(maxGrade, course.grade)
                    totalGrades += course.grade
                    totalStudents++
                }
            }
        }

        val avgGrade = if (totalStudents > 0) totalGrades / totalStudents else 0.0

        return Triple(minGrade, maxGrade, avgGrade)
    }
}