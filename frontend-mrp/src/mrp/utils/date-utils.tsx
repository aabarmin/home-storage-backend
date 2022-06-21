import { LocalDate } from "@js-joda/core";

export function mapDates<T>(
  startDate: LocalDate,
  endDate: LocalDate,
  f: (a: LocalDate) => T
): T[] {
  let currentDate = startDate;
  const result: T[] = [];

  while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
    result.push(f(currentDate));

    currentDate = currentDate.plusDays(1);
  }

  return result;
}
