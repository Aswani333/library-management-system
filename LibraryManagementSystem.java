import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagementSystem {

    // -------------------- BOOK CLASS --------------------
    static class Book {
        private int bookId;
        private String title;
        private String author;
        private boolean isIssued;

        public Book(int bookId, String title, String author) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.isIssued = false;
        }

        public int getBookId() {
            return bookId;
        }

        public String getTitle() {
            return title;
        }

        public boolean isIssued() {
            return isIssued;
        }

        public void issueBook() {
            isIssued = true;
        }

        public void returnBook() {
            isIssued = false;
        }

        public void displayBook() {
            System.out.println(bookId + " | " + title + " | " + author +
                    " | Status: " + (isIssued ? "Issued" : "Available"));
        }
    }

    // -------------------- MEMBER CLASS --------------------
    static class Member {
        private int memberId;
        private String name;
        private Book borrowedBook;
        private int issueDay;

        public Member(int memberId, String name) {
            this.memberId = memberId;
            this.name = name;
            this.borrowedBook = null;
            this.issueDay = 0;
        }

        public int getMemberId() {
            return memberId;
        }

        public Book getBorrowedBook() {
            return borrowedBook;
        }

        public int getIssueDay() {
            return issueDay;
        }

        public void borrowBook(Book book, int day) {
            borrowedBook = book;
            issueDay = day;
        }

        public void returnBorrowedBook() {
            borrowedBook = null;
            issueDay = 0;
        }

        public void displayMember() {
            System.out.println(memberId + " | " + name +
                    " | Borrowed Book: " +
                    (borrowedBook != null ? borrowedBook.getTitle() : "None"));
        }
    }

    // -------------------- LIBRARY CLASS --------------------
    static class Library {
        private ArrayList<Book> books;
        private ArrayList<Member> members;

        public Library() {
            books = new ArrayList<>();
            members = new ArrayList<>();
        }

        public void addBook(Book book) {
            books.add(book);
        }

        public void addMember(Member member) {
            members.add(member);
        }

        public void showBooks() {
            System.out.println("\n--- Library Books ---");
            for (Book b : books) {
                b.displayBook();
            }
        }

        public void showMembers() {
            System.out.println("\n--- Library Members ---");
            for (Member m : members) {
                m.displayMember();
            }
        }

        public Book findBook(int bookId) {
            for (Book b : books) {
                if (b.getBookId() == bookId)
                    return b;
            }
            return null;
        }

        public Member findMember(int memberId) {
            for (Member m : members) {
                if (m.getMemberId() == memberId)
                    return m;
            }
            return null;
        }

        // -------- ISSUE BOOK --------
        public void issueBook(int memberId, int bookId, int issueDay) {
            Member member = findMember(memberId);
            Book book = findBook(bookId);

            if (member == null || book == null) {
                System.out.println("Invalid Member ID or Book ID!");
                return;
            }

            if (book.isIssued()) {
                System.out.println("Book is already issued!");
                return;
            }

            if (member.getBorrowedBook() != null) {
                System.out.println("Member already borrowed a book!");
                return;
            }

            book.issueBook();
            member.borrowBook(book, issueDay);

            System.out.println("Book issued successfully!");
        }

        // -------- RETURN BOOK --------
        public void returnBook(int memberId, int returnDay) {
            Member member = findMember(memberId);

            if (member == null) {
                System.out.println("Invalid Member ID!");
                return;
            }

            Book borrowedBook = member.getBorrowedBook();

            if (borrowedBook == null) {
                System.out.println("No book borrowed by this member!");
                return;
            }

            int allowedDays = 7;
            int issuedDay = member.getIssueDay();

            int lateDays = (returnDay - issuedDay) - allowedDays;

            int fine = 0;
            if (lateDays > 0) {
                fine = lateDays * 2;
            }

            borrowedBook.returnBook();
            member.returnBorrowedBook();

            System.out.println("Book returned successfully!");

            if (fine > 0) {
                System.out.println("Late Fine: ₹" + fine);
            } else {
                System.out.println("Returned on time. No Fine!");
            }
        }
    }

    // -------------------- MAIN METHOD (MENU) --------------------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        // Sample Books
        library.addBook(new Book(101, "Java Programming", "James Gosling"));
        library.addBook(new Book(102, "OOPS Concepts", "Bjarne Stroustrup"));
        library.addBook(new Book(103, "Data Structures", "Mark Allen"));

        // Sample Members
        library.addMember(new Member(1, "Rahul"));
        library.addMember(new Member(2, "Priya"));

        int choice;

        do {
            System.out.println("\n========= LIBRARY MANAGEMENT SYSTEM =========");
            System.out.println("1. Show All Books");
            System.out.println("2. Show All Members");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    library.showBooks();
                    break;

                case 2:
                    library.showMembers();
                    break;

                case 3:
                    System.out.print("Enter Member ID: ");
                    int mid = sc.nextInt();

                    System.out.print("Enter Book ID: ");
                    int bid = sc.nextInt();

                    System.out.print("Enter Issue Day (1-30): ");
                    int issueDay = sc.nextInt();

                    library.issueBook(mid, bid, issueDay);
                    break;

                case 4:
                    System.out.print("Enter Member ID: ");
                    int memberId = sc.nextInt();

                    System.out.print("Enter Return Day (1-30): ");
                    int returnDay = sc.nextInt();

                    library.returnBook(memberId, returnDay);
                    break;

                case 5:
                    System.out.println("Exiting Library System...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}