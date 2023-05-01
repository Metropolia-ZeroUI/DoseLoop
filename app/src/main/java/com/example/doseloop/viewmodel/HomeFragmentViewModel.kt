package com.example.doseloop.viewmodel

class HomeFragmentViewModel : AbstractViewModel() {
    // TODO: Implement functionality
    /**
     * Implementation for displaying the next upcoming medicine taking time and day.
     * Commented because has issues in the functionality. Logic should be fixed
     * Start
     */
//
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!
//
//    @SuppressLint("SetTextI18n")
//    fun sortInputDataList(inputList: List<Triple<String, String, String>>) {
//
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//
//        val inputTimeList = mutableListOf<Triple<LocalDateTime, LocalTime, String>>()
//
//        inputList.forEach { (dateKey, timeKey, dayKey) ->
//            val savedDate = getFromPrefs(dateKey, "")
//            val savedTime = getFromPrefs(timeKey, "")
//            val savedDateTime = if (savedDate?.isNotEmpty() == true && savedTime?.isNotEmpty() == true) {
//                LocalDateTime.parse("$savedDate ${savedTime.padStart(5, '0')}", formatter)
//            } else {
//                null
//            }
//
//            val isEveryday = getFromPrefs(dayKey, false)
//            savedDateTime?.let { dateTime ->
//                var nextDateTime = dateTime
//                val currentDate = LocalDate.now(ZoneId.systemDefault())
//                val daysUntilNext = ChronoUnit.DAYS.between(currentDate, dateTime.toLocalDate()).toInt()
//
//                if (isEveryday) {
//                    if (daysUntilNext <= 0) {
//                        nextDateTime = dateTime.plusDays(1)
//                    } else if (daysUntilNext == 1) {
//                        nextDateTime = dateTime
//                    }
//                }
//
//                inputTimeList.add(Triple(nextDateTime, nextDateTime.toLocalTime(), isEveryday.toString()))
//            }
//        }
//
//        val sortedInputDataList = MutableLiveData<List<Triple<LocalDateTime, LocalTime, String>>>()
//
//        viewModelScope.launch {
//            val sortedList = inputTimeList.sortedWith(compareBy(
//                // First, sort by the closest upcoming time
//                { ChronoUnit.MINUTES.between(LocalDateTime.now(), it.first) },
//                // Next sort by true or false.
//                // If the item is false and goes to the present time, move it to the end of the list.
//                // If the item is true, count how many days are left to show,
//                // if there are no other nearer times in that interval, show that, if there are others, move it to the end of the list.
//                { if (it.third == "false" && it.first.isBefore(LocalDateTime.now())) Int.MAX_VALUE else if (it.third == "true") ChronoUnit.DAYS.between(LocalDate.now(ZoneId.systemDefault()), it.first.toLocalDate()) else -1 },
//                { Duration.between(LocalTime.now(ZoneId.systemDefault()), it.second) }
//            ))
//
//            sortedInputDataList.postValue(sortedList)
//
//            // Update the UI with the next upcoming date and time
//            sortedList.firstOrNull()?.let { (dateTime, time, isEveryday) ->
//                val currentDate = LocalDate.now(ZoneId.systemDefault())
//
//                val dayText = if (isEveryday == "true") {
//                    val daysUntilNext = ChronoUnit.DAYS.between(currentDate, dateTime.toLocalDate()).toInt()
//                    when {
//                        daysUntilNext == 0 -> "today"
//                        daysUntilNext == 1 -> "tomorrow"
//                        else -> dateTime.format(DateTimeFormatter.ofPattern("EEE"))
//                    }
//                } else {
//                    "everyday"
//                }
//
//                binding.tvTime.text = time.format(DateTimeFormatter.ofPattern("HH:mm"))
//                binding.tvDay.text = dayText
//            } ?: run {
//                binding.tvTime.text = "__:__"
//                binding.tvDay.text = "Unknown day"
//            }
//        }
//    }
    /**
     * End
     */
}
